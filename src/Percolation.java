import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final WeightedQuickUnionUF grid;
    private boolean[][] openedSpaces;
    private final int side;
    private final int bottom;
    private final int TOP;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("index " + n + " is > than 1 ");
        side = n;
        TOP = (side * side);
        bottom = (side * side) + 1;
        grid = new WeightedQuickUnionUF(side * side + 2);
        openedSpaces = new boolean[side][side];
    }

    // opens the site (row, col) if it is not open already
    public void open(int i, int j) {
        this.validate(i);
        this.validate(j);

        openedSpaces[i - 1][j - 1] = true;

        // top
        if (i == 1) {
            grid.union(getGridIndex(i, j), TOP);
        }
        // bottom
        if (i == side) {
            grid.union(getGridIndex(i, j), bottom);
        }
        // upper
        if (i > 1 && i <= side && isOpen(i - 1, j)) {
            grid.union(getGridIndex(i, j), getGridIndex(i - 1, j));
        }
        // down
        if (i >= 1 && i < side && isOpen(i + 1, j)) {
            grid.union(getGridIndex(i, j), getGridIndex(i + 1, j));
        }
        // left <-
        if (j > 1 && isOpen(i, j - 1)) {
            grid.union(getGridIndex(i, j), getGridIndex(i, j - 1));
        }
        // right ->
        if (j < side && isOpen(i, j + 1)) {
            grid.union(getGridIndex(i, j), getGridIndex(i, j + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int i, int j) {
        validate(i);
        validate(j);
        return openedSpaces[i - 1][j - 1];
    }


    // returns the number of open sites
    public int numberOfOpenSites() {
        int opened = 0;
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                if (openedSpaces[i][j]) {
                    opened++;
                }
            }
        }
        return opened;
    }

    // is the site (row, col) full?
    public boolean isFull(int i, int j) {
        if (0 < i && i <= side && 0 < j && j <= side) {
            return grid.find(TOP) == grid.find(getGridIndex(i, j));
        } else {
            throw new IllegalArgumentException();
        }
    }

    // DOes the system percolate?
    public boolean percolates() {
        return grid.find(TOP) == grid.find(bottom);
    }

    private int getGridIndex(int i, int j) {
        return side * (i - 1) + (j - 1);
    }

    private void validate(int p) {
        int n = side;
        if (p < 1 || p > n) {
            throw new IllegalArgumentException("index " + p + " is not between 1 and " + (n));
        }
    }
}
