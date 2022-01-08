package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] opened;
    private final int sideLength;
    private final WeightedQuickUnionUF unionFind;
    private int numOpened;

    public Percolation(int N) {
        // create N-by-N grid, with all sites initially blocked
        this.opened = new boolean[N][N];
        this.sideLength = N;
        this.unionFind = new WeightedQuickUnionUF(flattenLocation(N, N));
        this.numOpened = 0;
    }

    public void open(int row, int col) {
        validateLocation(row, col);

        int flatLoc = flattenLocation(row, col);
        int[] neighborRows = new int[]{row, row, row + 1, row - 1};
        int[] neighborCols = new int[]{col - 1, col + 1, col, col};
        for (int i = 0; i < 4; i++) {
            int neighborRow = neighborRows[i];
            int neighborCol = neighborCols[i];

            if (isValidLocation(neighborRow, neighborCol) && opened[neighborRow][neighborCol]) {
                unionFind.union(flatLoc, flattenLocation(neighborRow, neighborCol));
            }
        }
        opened[row][col] = true;
        numOpened += 1;
    }

    public boolean isOpen(int row, int col) {
        validateLocation(row, col);
        return opened[row][col];
    }

    public boolean isFull(int row, int col) {
        validateLocation(row, col);

        int flatLoc = flattenLocation(row, col);
        boolean full = false;
        for (int i = 0; i < sideLength; i++) {
            if (opened[0][i] && unionFind.connected(flatLoc, flattenLocation(0, i))) {
                full = true;
                break;
            }
        }
        return full;
    }

    public int numberOfOpenSites() {
        return numOpened;
    }

    public boolean percolates() {
        boolean doesPercolate = false;
        outer:
        for (int btmCol = 0; btmCol < sideLength; btmCol++) {
            for (int topCol = 0; topCol < sideLength; topCol++) {
                if (opened[0][topCol] && opened[sideLength - 1][btmCol] && unionFind.connected(flattenLocation(0, topCol), flattenLocation(sideLength - 1, btmCol))) {
                    doesPercolate = true;
                    break outer;
                }
            }
        }
        return doesPercolate;
    }

    private void validateLocation(int row, int col) {
        if (row < 0 || row >= sideLength - 1 || col < 0 || col >= sideLength - 1) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean isValidLocation(int row, int col) {
        return row < 0 || row > sideLength - 1 || col < 0 || col >= sideLength - 1;
    }

    private int flattenLocation(int row, int col) {
        return row * sideLength + col;
    }

}
