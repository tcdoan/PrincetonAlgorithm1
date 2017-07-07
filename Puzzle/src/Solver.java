import edu.princeton.cs.algs4.*;

public class Solver {
    private boolean isSolvable;
    private Move solution;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException("Initial board is null");
        }

        MinPQ<Move> masterQueue = new MinPQ<>();
        MinPQ<Move> twinQueue = new MinPQ<>();

        masterQueue.insert(new Move(initial, 0, null));
        twinQueue.insert(new Move(initial.twin(), 0, null));

        isSolvable = false;
        while (masterQueue.size() > 0 && twinQueue.size() > 0) {
            Move twinMove = twinQueue.delMin();
            if (twinMove.board != null) {
                if (twinMove.board.isGoal()) {
                    isSolvable = false;
                    break;
                }
                solution = masterQueue.delMin();
                if (solution != null && solution.board != null) {
                    if (solution.board.isGoal()) {
                        isSolvable = true;
                        break;
                    }

                    for (Board twinNeighbor : twinMove.board.neighbors()) {
                        if (twinNeighbor != null && (twinMove.prev == null || !twinNeighbor.equals(twinMove.prev.board))) {
                            twinQueue.insert(new Move(twinNeighbor, twinMove.numMovesToReach + 1, twinMove));
                        }
                    }

                    for (Board n : solution.board.neighbors()) {
                        if (n != null && (solution.prev == null || !n.equals(solution.prev.board))) {
                            masterQueue.insert(new Move(n, solution.numMovesToReach + 1, solution));
                        }
                    }
                }
            }
        }
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable) {
            return solution.numMovesToReach;
        }
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
            ResizingArrayStack<Board> ss = new ResizingArrayStack<>();
            Move x = solution;
            while (x != null) {
                ss.push(x.board);
                x = x.prev;
            }
            return ss;
        }
        return null;
    }

    private class Move implements Comparable<Move> {
        private Board board;
        private int numMovesToReach;
        private Move prev;
        private int manhattan;

        public Move(Board board, int numMovesToReach, Move prev) {
            this.board = board;
            this.numMovesToReach = numMovesToReach;
            this.prev = prev;
            this.manhattan = board.manhattan();
        }

        public int priority() {
            return numMovesToReach + manhattan;
        }

        @Override
        public int compareTo(Move other) {
            if (priority() == other.priority()) {
                return manhattan - other.manhattan;
            }
            return priority() - other.priority();
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }
}
