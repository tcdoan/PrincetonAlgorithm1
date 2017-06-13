import edu.princeton.cs.algs4.RandomSeq;
import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.StdRandom;

/**
 * Created by Thanh on 6/12/2017.
 */
public class Board
{
    private int n;
    private int tiles[][];
    private int goal[][];


    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks)
    {
        if (blocks == null)
        {
            throw new NullPointerException("blocks is null.");
        }
        n = blocks.length;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                tiles[i][j] = blocks[i][j];
            }
        }

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (i == n-1 && j == n-1)
                {
                    goal[i][j] = 0;
                } else {
                    goal[i][j] = i*n + j + 1;
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
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (tiles[i][j] != goal[i][j])
                {
                    dist+=1;
                }
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
                // (y0, x0) s.t. goal[y0][x0] = tiles[y][x]
                int y0= tiles[y][x]/n;
                int x0= tiles[y][x] % n;
                dist = dist + (Math.abs(y0 - y) + Math.abs(x0 - x));
            }
        }
        return  dist;
    }

    // is this board the goal board?
    public boolean isGoal()
    {
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (tiles[i][j] != goal[i][j])
                {
                    return false;
                }
            }
        }
        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin()
    {
        while (true)
        {
            int x = StdRandom.uniform(n);
            int y = StdRandom.uniform(n);

            if (tiles[y][x] != 0)
            {
                if (y > 0 && tiles[y-1][x] != 0)
                {
                    return twinAt(y, x, y-1, x);
                }
                if (y < n-1 && tiles[y+1][x] != 0)
                {
                    return twinAt(y, x, y+1, x);
                }
                if (x > 0 && tiles[y][x-1] != 0)
                {
                    return twinAt(y, x, y, x-1);
                }
                if (x < n-1 && tiles[y][x+1] != 0)
                {
                    return twinAt(y, x, y, x+1);
                }
            }
        }
    }

    private Board twinAt(int y1, int x1, int x2, int y2)
    {
        int[][] copy  = new int[n][n];
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                copy[i][j] = tiles[i][j];
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
            throw new NullPointerException();
        }

        if (!(y instanceof Board))
        {
            return false;
        }

        Board b = (Board) y;
        for (int i = 0; i < b.tiles.length; i++)
        {
            for (int j = 0; j < b.tiles.length; j++)
            {
                if (tiles[i][j] != b.tiles[i][j])
                {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors()
    {
        ResizingArrayQueue<Board> queue = new ResizingArrayQueue<>();
        //TBD
        return queue;
    }

    // string representation of this board (in the output format specified below)
    public String toString()
    {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    // unit tests (not graded)
    public static void main(String[] args)
    {

    }
}
