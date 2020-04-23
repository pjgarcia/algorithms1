import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] computations;

    public PercolationStats(int n, int trials) {
        this.computations = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation trial = percolationTrial(n);
            this.computations[i] = ((float) trial.numberOfOpenSites()) / n*n;
        }
    }

    public Percolation percolationTrial(int n) {
        Percolation p = new Percolation(n);

        while (!p.percolates()) {
            int nextRow, nextCol;
            do {
                nextRow = StdRandom.uniform(n);
                nextCol = StdRandom.uniform(n);
            } while (p.isOpen(nextRow, nextCol));

            p.open(nextRow, nextCol);
        }
        return p;
    }

    public double mean() {
        return StdStats.mean(this.computations);
    }

    public double stddev() {
        return StdStats.stddev(this.computations);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(computations.length));
    }

    public double confidenceHi() {
        return mean() - (1.96 * stddev() / Math.sqrt(computations.length));
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(
                Integer.valueOf(args[0]),
                Integer.valueOf(args[1]));

        System.out.printf(
                "mean\t\t\t= %f\n" +
                "stddev\t\t\t= %f\n" +
                "95%% confidence interval = [%f, %f]\n",
                ps.mean(), ps.stddev(), ps.confidenceLo(), ps.confidenceHi());
    }
}
