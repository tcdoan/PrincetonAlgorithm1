import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.ResizingArrayQueue;
import edu.princeton.cs.algs4.StdOut;

public class Solver
{

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)
    {
        MinPQ<SearchNode> masterQueue = new MinPQ<>();
        MinPQ<SearchNode> twinQueue = new MinPQ<>();

        SearchNode firstSearchNode = new SearchNode(initial,0,null);
        SearchNode firstTwinNode = new SearchNode(initial.twin(),0,null);

        masterQueue.insert(firstSearchNode);
        twinQueue.insert(firstTwinNode);

        isSolvable = false;
        while (masterQueue.size() > 0 && twinQueue.size() > 0)
        {
            SearchNode twinNode = twinQueue.delMin();
            if (twinNode != null && twinNode.board != null)
            {
                twinSolution.enqueue(twinNode.board);
                if (twinNode.board.isGoal())
                {
                    isSolvable = false;
                    break;
                }
                else
                {
                    SearchNode current = masterQueue.delMin();
                    if (current != null && current.board != null)
                    {
                        solution.enqueue(current.board);
                        if (current.board.isGoal())
                        {
                            isSolvable = true;
                            break;
                        }

                        for (Board twinNeighbor : twinNode.board.neighbors())
                        {
                            if (twinNeighbor != null && twinNode.prev != null && !twinNeighbor.equals(twinNode.prev.board))
                            {
                                twinQueue.insert(new SearchNode(twinNeighbor, twinSolution.size(), twinNode));
                            }
                        }

                        for (Board n : current.board.neighbors())
                        {
                            if (n != null && current.prev != null && !n.equals(current.prev.board))
                            {
                                masterQueue.insert(new SearchNode(n, solution.size(), current));
                            }
                        }
                    }
                }
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable()
    {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves()
    {
        if (isSolvable)
        {
            return solution.size()-1;
        }
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution()
    {
        if (isSolvable())
        {
            return solution;
        }
        return null;
    }

    private boolean isSolvable;
    private ResizingArrayQueue<Board> solution = new ResizingArrayQueue<>();
    private ResizingArrayQueue<Board> twinSolution = new ResizingArrayQueue<>();

    private class SearchNode implements Comparable<SearchNode>
    {
        private Board board;
        private int numMovesToReach;
        private SearchNode prev;

        public SearchNode(Board board, int numMovesToReach, SearchNode prev)
        {
            this.board = board;
            this.numMovesToReach = numMovesToReach;
            this.prev = prev;
        }

        public int priority()
        {
            return numMovesToReach + this.board.manhattan();
        }

        @Override
        public int compareTo(SearchNode other)
        {
            return priority() - other.priority();
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args)
    {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
        {
            StdOut.println("No solution possible");
        }
        else
        {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
