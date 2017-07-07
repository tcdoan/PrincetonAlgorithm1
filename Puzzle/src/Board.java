import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by Thanh on 6/12/2017.
 */
public class Board
{
    private int n;
    private int tiles[];
    private int i0;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks)
    {
        if (blocks == null)
        {
            throw new IllegalArgumentException("Inout blocks is null");
        }

        n = blocks.length;
        tiles = new int[n*n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                tiles[i*n + j] = blocks[i][j];
                if (tiles[i*n + j] == 0)
                {
                    i0 = i*n + j;
                }
            }
        }
    }

    // board dimension n
    public int dimension()
    {
        return n;
    }

    // number of blocks out of place
    public int hamming()
    {
        int dist = 0;
        for (int i = 0; i < n*n; i++)
        {
            if (tiles[i] != 0 && tiles[i] != i+1)
            {
                dist+=1;
            }
        }
        return  dist;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan()
    {
        int dist = 0;
        for (int y = 0; y < n; y++)
        {
            for (int x = 0; x < n; x++)
            {
                if (tiles[y*n + x] != 0)
                {
                    // expected y, expected x
                    int ey = (tiles[y*n + x] -1) / n;
                    int ex = (tiles[y*n + x] -1) % n;
                    dist = dist + Math.abs(ey - y) + Math.abs(ex - x);
                }
            }
        }
        return  dist;
    }

    // is this board the goal board?
    public boolean isGoal()
    {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin()
    {
        int i = 0;
        while (true)
        {
            int x = StdRandom.uniform(n);
            int y = StdRandom.uniform(n);

            if (tiles[y*n+x] != 0)
            {
                if (y > 0 && tiles[n*(y-1)+x] != 0)
                {
                    return twinAt(y, x, y-1, x);
                }
                if (y < n-1 && tiles[n*(y+1)+x] != 0)
                {
                    return twinAt(y, x, y+1, x);
                }
                if (x > 0 && tiles[n*y +x-1] != 0)
                {
                    return twinAt(y, x, y, x-1);
                }
                if (x < n-1 && tiles[n*y + x+1] != 0)
                {
                    return twinAt(y, x, y, x+1);
                }
            }
        }
    }

    private Board twinAt(int y1, int x1, int y2, int x2)
    {
        int[][] copy  = new int[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                copy[i][j] = tiles[i*n + j];
            }
        }
        int tmp = copy[y1][x1];
        copy[y1][x1] = copy[y2][x2];
        copy[y2][x2] = tmp;
        return new Board(copy);
    }

    // does this board equal y?
    public boolean equals(Object y)
    {
        if (y == null)
        {
            return false;
        }

        if (!(y instanceof Board))
        {
            return false;
        }

        Board b = (Board) y;
        if (b.tiles.length != this.tiles.length)
        {
            return false;
        }

        for (int i = 0; i < b.tiles.length; i++)
        {
            if (tiles[i] != b.tiles[i])
            {
                return false;
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        ResizingArrayQueue<Board> queue = new ResizingArrayQueue<>();
        int y0 = i0 / n;
        int x0 = i0 % n;

        if (y0 > 0)
        {
            Board north = swapAndGetBoardAt(y0, x0, y0-1, x0);
            queue.enqueue(north);
        }
        if (y0 < n-1)
        {
            Board south = swapAndGetBoardAt(y0, x0, y0+1, x0);
            queue.enqueue(south);
        }
        if (x0 > 0)
        {
            Board east = swapAndGetBoardAt(y0, x0, y0, x0-1);
            queue.enqueue(east);
        }
        if (x0 < n-1)
        {
            Board west = swapAndGetBoardAt(y0, x0, y0, x0+1);
            queue.enqueue(west);
        }
        return queue;
    }

    private Board swapAndGetBoardAt(int y1, int x1, int y2, int x2)
    {
        int[][] copy  = new int[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                copy[i][j] = tiles[i*n+j];
            }
        }
        int tmp = copy[y1][x1];
        copy[y1][x1] = copy[y2][x2];
        copy[y2][x2] = tmp;
        return new Board(copy);
    }

    // string representation of this board (in the output format specified below)
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i*n+j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    // unit tests (not graded)
    public static void main(String[] args)
    {
        int[][] blocks = new int[3][3];
        blocks[0][0] = 8;
        blocks[0][1] = 1;
        blocks[0][2] = 3;

        blocks[1][0] = 4;
        blocks[1][1] = 0;
        blocks[1][2] = 2;

        blocks[2][0] = 7;
        blocks[2][1] = 6;
        blocks[2][2] = 5;

        Board b = new Board(blocks);
        System.out.println("Board "  + b.toString());
        System.out.printf("hamming = %d, manhattan = %d \n" , b.hamming(), b.manhattan());
        assert (b.hamming() == 5);
        assert (b.manhattan() == 10);

        Board b2 = new Board(blocks);
        System.out.printf("b2.isGoal() = %s, b2 eq b is %s \n" , Boolean.toString(b2.isGoal()), Boolean.toString(b2.equals(b)));

        Board twin = b.twin();
        System.out.println("b.twin() "  + twin.toString());


        // create search node
        System.out.println("create search node... " );
        blocks[1][1] = 2;
        blocks[1][2] = 0;
        Board s = new Board(blocks);
        System.out.println("search node "  + s.toString());

        System.out.println("search neighbors... " );
        for (Board n : s.neighbors())
        {
            System.out.println(n.toString());
        }

    }
}
