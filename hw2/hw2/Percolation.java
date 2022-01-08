package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final boolean[][] opened;
    private final int sideLength;
    private final WeightedQuickUnionUF unionFind;
    private final int posVirtualTop;
    private final int posVirtualBtm;
    private int numOpened;

    public Percolation(int N) {
        // create N-by-N grid, with all sites initially blocked
        this.opened = new boolean[N][N];
        this.sideLength = N;
        int numSites = flattenLocation(N, N);
        this.unionFind = new WeightedQuickUnionUF(numSites + 2);
        this.posVirtualTop = numSites;
        this.posVirtualBtm = numSites + 1;
        this.numOpened = 0;

        for (int col = 0; col < sideLength; col++) {
            unionFind.union(posVirtualTop, flattenLocation(0, col));
            unionFind.union(posVirtualBtm, flattenLocation(sideLength - 1, col));
        }
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
        return unionFind.connected(flattenLocation(row, col), posVirtualTop);
    }

    public int numberOfOpenSites() {
        return numOpened;
    }

    public boolean percolates() {
        return unionFind.connected(posVirtualBtm, posVirtualTop);
    }

    private void validateLocation(int row, int col) {
        if (row < 0 || row > sideLength - 1 || col < 0 || col > sideLength - 1) {
            throw new IndexOutOfBoundsException();
        }
    }

    private boolean isValidLocation(int row, int col) {
        return row >= 0 && row < sideLength && col >= 0 && col < sideLength;
    }

    private int flattenLocation(int row, int col) {
        return row * sideLength + col;
    }

}
