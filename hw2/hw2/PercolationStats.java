package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private final double[] fraction;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("Grid size N or perform times T needs to be greater than 0!");
        }
        Percolation percolation = pf.make(N);
        fraction = new double[T];
        int row, col;
        for (int i = 0; i < T; i++) {
            int openedSiteNum = 0;
            while (!percolation.percolates()) {
                row = StdRandom.uniform(N);
                col = StdRandom.uniform(N);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                    openedSiteNum += 1;
                }
            }
            fraction[i] = (double)(openedSiteNum) / (double) (N * N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fraction);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fraction);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(fraction.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(fraction.length);
    }
}
