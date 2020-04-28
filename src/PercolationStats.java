import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] computations;
    private static final double CONFIDENCE_95 = 1.98;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 0) throw new IllegalArgumentException("index " + n + " is > than 0 ");
        this.computations = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation trial = percolationTrial(n);
            this.computations[i] = (double) trial.numberOfOpenSites() / (n * n);
        }
    }

    // perform one trial on an n-by-n grid
    private Percolation percolationTrial(int n) {
        Percolation p = new Percolation(n);

        while (!p.percolates()) {
            int nextRow, nextCol;
            do {
                nextRow = StdRandom.uniform(n);
                nextCol = StdRandom.uniform(n);
            } while (p.isOpen(nextRow + 1, nextCol + 1));
            p.open(nextRow + 1, nextCol + 1);
        }
        return p;
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(this.computations);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(this.computations);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(computations.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(computations.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(
                Integer.parseInt(args[0]),
                Integer.parseInt(args[1]));

        System.out.printf(
                "mean\t\t\t= %f%n" +
                        "stddev\t\t\t= %f%n" +
                        "95%% confidence interval = [%f, %f]%n",
                ps.mean(), ps.stddev(), ps.confidenceLo(), ps.confidenceHi());
    }
}
