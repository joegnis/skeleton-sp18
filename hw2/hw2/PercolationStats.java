package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private final double[] ratioOpenSites;
    private final int numExperiments;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N < 0 || T < 0) {
            throw new IllegalArgumentException("N or T is negative");
        }
        this.ratioOpenSites = new double[T];
        this.numExperiments = T;

        for (int exp = 0; exp < T; exp++) {
            Percolation percolation = pf.make(N);

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
