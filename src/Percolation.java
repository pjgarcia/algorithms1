import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF unionFind;
    private int size;
    private int topNodeIndex;
    private int bottomNodeIndex;
    private boolean[] opennesGrid;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.size = n;
        // los 2 extras son para manejar las puntas, para preguntar si percola
        this.unionFind = new WeightedQuickUnionUF(n * n + 2);

        this.topNodeIndex = this.size * this.size;
        this.bottomNodeIndex = this.size * this.size + 1;

        this.opennesGrid = new boolean[this.size * this.size];
        for (int i = 0; i < this.size * this.size; i++) {
            this.opennesGrid[i] = false;
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        int center = row * size + col;
        int up = ((row - 1) * size) + col;
        int down = ((row + 1) * size) + col;
        int left = row * size + col - 1;
        int right = row * size + col + 1;

        opennesGrid[row * size + col] = true;

        // caso para unirlo con el nodo especial de arriba
        if (row == 0) {
            unionFind.union(center, topNodeIndex);
        }

        // tiene espacios arriba
        if (row > 0 && isOpen(row - 1, col)) {
            unionFind.union(center, up);
        }

        // tiene espacios abajo
        if (row < (size - 2) && isOpen(row + 1, col)) {
            unionFind.union(center, down);
        }

        // tiene espacios a la izquierda
        if (col > 0 && isOpen(row, col - 1)) {
            unionFind.union(center, left);
        }

        // tiene espacios a la derecha
        if (col < (size - 2) && isOpen(row, col + 1)) {
            unionFind.union(center, right);
        }

        // caso para unirlo con el nodo especial de abajo
        if (row == (size - 1)) {
            unionFind.union(center, bottomNodeIndex);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return this.opennesGrid[row * size + col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        boolean connectedToTop = unionFind.find(row * size + col) == unionFind.find(topNodeIndex);
        boolean connectedToBottom = unionFind.find(row * size + col) == unionFind.find(bottomNodeIndex);

        return connectedToTop && connectedToBottom;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < size * size; i++) {
            if (opennesGrid[i]) count++;
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return unionFind.find(topNodeIndex) == unionFind.find(bottomNodeIndex);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
