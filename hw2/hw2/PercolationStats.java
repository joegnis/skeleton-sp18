package hw2;

import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] ratioOpenSites;
    private final int numExperiments;

    public <U extends IUnionFind> PercolationStats(int N, int T, PercolationFactory pf, Class<U> unionFindClass) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N or T is not a positive integer");
        }
        this.ratioOpenSites = new double[T];
        this.numExperiments = T;

        for (int exp = 0; exp < T; exp++) {
            Percolation percolation = pf.make(N, unionFindClass);

            while (!percolation.percolates()) {
                while (true) {
                    int nextPos = StdRandom.uniform(N * N);
                    int nextRow = nextPos / N;
                    int nextCol = nextPos % N;

                    if (!percolation.isOpen(nextRow, nextCol)) {
                        percolation.open(nextRow, nextCol);
                        break;
                    }
                }
            }

            ratioOpenSites[exp] = percolation.numberOfOpenSites() / (double) (N * N);
        }
    }

    public PercolationStats(int N, int T, PercolationFactory pf) {
        this(N, T, pf, QuickUnion.class);
    }

    public double mean() {
        return StdStats.mean(ratioOpenSites);
    }

    public double stddev() {
        return StdStats.stddev(ratioOpenSites);
    }

    public double confidenceLow() {
        double mean = mean();
        double stdDev = stddev();
        return mean - 1.96 * stdDev / Math.sqrt(numExperiments);
    }

    public double confidenceHigh() {
        double mean = mean();
        double stdDev = stddev();
        return mean + 1.96 * stdDev / Math.sqrt(numExperiments);
    }

    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(20, 100, new PercolationFactory());
        System.out.printf("Mean: %.4f, StdDev: %.4f, 95 confidence low: %.4f, high: %.4f\n", stats.mean(), stats.stddev(), stats.confidenceLow(), stats.confidenceHigh());
    }
}
