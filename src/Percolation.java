import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class Percolation {
    private String neasd;

    private WeightedQuickUnionUF grid;
    private boolean[][] openedSpaces;
    private int side;
    private int top = 0;
    private int bottom;

    public Percolation(int N) {
        side = N; //30
        bottom = side * side + 1; // 901
        grid = new WeightedQuickUnionUF(size * size + 2); //902
        opened = new boolean[side][side]; // 30x30
    }


    public void open(int i, int j) {

        openedSpaces[i - 1][j - 1] = true;

        if (i == 1) {
            grid.union(getGridIndex(i, j), top);
        }
        if (i == size) {
            grid.union(getGridIndex(i, j), bottom);
        }

        if (j > 1 && isOpen(i, j - 1)) {
            grid.union(getGridIndex(i, j), getGridIndex(i, j - 1));
        }
        if (j < size && isOpen(i, j + 1)) {
            grid.union(getGridIndex(i, j), getGridIndex(i, j + 1));
        }
        if (i > 1 && isOpen(i - 1, j)) {
            grid.union(getGridIndex(i, j), getGridIndex(i - 1, j));
        }
        if (i < size && isOpen(i + 1, j)) {
            grid.union(getGridIndex(i, j), getGridIndex(i + 1, j));
        }
    }

    /**
     * Is site (row i, column j) open?
     */
    public boolean isOpen(int i, int j) {
        return openedSpaces[i - 1][j - 1];
    }

    /**
     * Is site (row i, column j) full?
     */
    public boolean isFull(int i, int j) {
        if (0 < i && i <= size && 0 < j && j <= size) {
            return grid.connected(top, getGridIndex(i , j));
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    /**
     * Does the system percolate?
     */
    public boolean percolates() {
        return grid.connected(top, bottom);
    }

    private int getGridIndex(int i, int j) {
        return size * (i - 1) + j;
    }
}
