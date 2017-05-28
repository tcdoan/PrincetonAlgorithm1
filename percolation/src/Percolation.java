import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int VIRTUAL_SITE_NUM = 2;
    private boolean[][] _sites;
    private int _numberOfOpenSites;
    private WeightedQuickUnionUF _weightedQuickUnionUF;

    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException("Could not create Percolation with grid size negative or zero");
        }

        _sites = new boolean[n][n];
        _numberOfOpenSites = 0;
        _weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + VIRTUAL_SITE_NUM);

        for (int i = 1; i <= _sites.length; i++) {
            _weightedQuickUnionUF.union(0, i);
            _weightedQuickUnionUF.union(rowColToQuickUnionIndex(_sites.length, i), _sites.length * _sites.length + 1);
        }

    }

    private boolean isValidSite(int row, int col) {
        if (row < 1 || row > _sites.length || col < 1 || col > _sites.length) {
            return false;
        }
        return true;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        validateRowCol(row, col);

        if (!_sites[row - 1][col - 1]) {
            _sites[row - 1][col - 1] = true;
            _numberOfOpenSites += 1;

            // north site
            if (isValidSite(row - 1, col) && isOpen(row - 1, col)) {
                _weightedQuickUnionUF.union(rowColToQuickUnionIndex(row - 1, col), rowColToQuickUnionIndex(row, col));
            }
            if (isValidSite(row + 1, col) && isOpen(row + 1, col)) // south
            {
                _weightedQuickUnionUF.union(rowColToQuickUnionIndex(row + 1, col), rowColToQuickUnionIndex(row, col));
            }
            if (isValidSite(row, col - 1) && isOpen(row, col - 1)) // east
            {
                _weightedQuickUnionUF.union(rowColToQuickUnionIndex(row, col - 1), rowColToQuickUnionIndex(row, col));
            }
            if (isValidSite(row, col + 1) && isOpen(row, col + 1)) // west
            {
                _weightedQuickUnionUF.union(rowColToQuickUnionIndex(row, col + 1), rowColToQuickUnionIndex(row, col));
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        validateRowCol(row, col);
        return _sites[row - 1][col - 1];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        validateRowCol(row, col);
        int q = rowColToQuickUnionIndex(row, col);
        if (isOpen(row, col)) {
            return _weightedQuickUnionUF.connected(0, q);
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return _numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (_numberOfOpenSites > 0) {
            return _weightedQuickUnionUF.connected(0, (_sites.length * _sites.length) + 1);
        }
        return false;
    }

    private int rowColToQuickUnionIndex(int row, int col) {
        return (row - 1) * _sites.length + col ;
    }

    private void validateRowCol(int row, int col) {
        if (row < 1 || row > _sites.length) {
            throw new java.lang.IndexOutOfBoundsException("Invalid row number");
        }
        if (col < 1 || col > _sites.length) {
            throw new java.lang.IndexOutOfBoundsException("Invalid column number");
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        In in = new In(args[0]);      // input file
        int n = in.readInt();
        Percolation percolation = new Percolation(n);
        System.out.printf("is percolated =  %s \n", Boolean.toString(percolation.percolates()));
        while (!in.isEmpty()) {
            int row = in.readInt();
            int col = in.readInt();
            percolation.open(row,col);
            System.out.printf("isOPen(%d, %d) = %s  \n", row, col, Boolean.toString(percolation.isOpen(row,col)));
            System.out.printf("isFull (%d, %d) = %s \n", row, col, Boolean.toString(percolation.isFull(row,col)));
            System.out.printf("is percolated =  %s \n", Boolean.toString(percolation.percolates()));
        }
    }
}
