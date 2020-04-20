import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] computations;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        this.computations = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation trial = percolationTrial(n);
            // [55 / (20*20), 70*(20*20), 0.55]
            this.computations[i] = (float)trial.numberOfOpenSites() / (n*n);
            //System.out.printf("computation #%d finished: %f%n", i+1, this.computations[i]);
        }
    }

    // perform one trial on an n-by-n grid
    public Percolation percolationTrial(int n) {
        Percolation p = new Percolation(n);

        while (!p.percolates()) {
            int nextRow, nextCol;
            do {
                nextRow = StdRandom.uniform(n);
                nextCol = StdRandom.uniform(n);
            } while (p.isOpen(nextRow, nextCol));
            //System.out.printf("opening (%d, %d)\n", nextRow, nextCol);
            p.open(nextRow, nextCol);
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
        return mean() - (1.96 * stddev() / Math.sqrt(computations.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(computations.length));
    }

    // test client (see below)
    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(
                Integer.valueOf(args[0]),
                Integer.valueOf(args[1]));

        System.out.printf(
                "mean\t\t\t= %f%n" +
                "stddev\t\t\t= %f%n" +
                "95%% confidence interval = [%f, %f]%n",
                ps.mean(), ps.stddev(), ps.confidenceLo(), ps.confidenceHi());
    }
}
