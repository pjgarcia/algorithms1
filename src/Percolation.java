
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF grid;
    private boolean[][] openedSpaces;
    private int side;
    private int top = 0;
    private int bottom;
    private int opened;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int N) {
        side = N;
        bottom = side * side + 1;
        grid = new WeightedQuickUnionUF(side * side + 2);
        openedSpaces = new boolean[side+1][side+1];
    }

    // opens the site (row, col) if it is not open already
    public void open(int i, int j) {

        openedSpaces[i][j] = true;

        if (i == 1) {
            grid.union(getGridIndex(i, j), top);
        }
        if (i == side) {
            grid.union(getGridIndex(i, j), bottom);
        }

        if (i > 1 && isOpen(i - 1, j)) {
            grid.union(getGridIndex(i, j), getGridIndex(i - 1, j));
        }
        if (i < side && isOpen(i + 1, j)) {
            grid.union(getGridIndex(i, j), getGridIndex(i + 1, j));
        }

        if (j > 1 && isOpen(i, j - 1)) {
            grid.union(getGridIndex(i, j), getGridIndex(i, j - 1));
        }
        if (j < side && isOpen(i, j + 1)) {
            grid.union(getGridIndex(i, j), getGridIndex(i, j + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int i, int j) {
        return openedSpaces[i][j];
    }

    // is the site (row, col) full?
    public boolean isFull(int i, int j) {
        if (0 < i && i <= side && 0 < j && j <= side) {
            return grid.connected(top, getGridIndex(i , j));
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        for (int i = 0; i < side ; i++) {
            for (int j = 0; j < side; j++) {
                if(openedSpaces[i][j]){
                    opened ++;
                }
            }
        }
        return opened;
    }

    //Does the system percolate?
    public boolean percolates() {
        return grid.connected(top, bottom);
    }

    private int getGridIndex(int i, int j) {
        return side * i  + j;
    }
}
