package hw2;

import java.lang.reflect.InvocationTargetException;

public class Percolation {
    private final boolean[][] opened;
    private final int sideLength;
    private final IUnionFind unionFind;
    private final IUnionFind unionFindFull;  // for testing fullness
    private final int posVirtualTop;
    private final int posVirtualBtm;
    private int numOpened;

    public <U extends IUnionFind> Percolation(int N, Class<U> unionFindClass) {
        // create N-by-N grid, with all sites initially blocked
        if (N <= 0) {
            throw new IllegalArgumentException("Non-positive N: " + N);
        }
        this.opened = new boolean[N][N];
        this.sideLength = N;
        int numSites = flattenLocation(N, N);
        this.posVirtualTop = numSites;
        this.posVirtualBtm = numSites + 1;
        this.numOpened = 0;

        try {
            this.unionFind = unionFindClass.getDeclaredConstructor(int.class).newInstance(numSites + 2);
            this.unionFindFull = unionFindClass.getDeclaredConstructor(int.class).newInstance(numSites + 1);
        } catch (NoSuchMethodException|IllegalAccessException|IllegalArgumentException|InstantiationException|InvocationTargetException ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Failed to initialize union find objects.");
        }
    }

    public Percolation(int N) {
        this(N, QuickUnion.class);
    }

    public void open(int row, int col) {
        validateLocation(row, col);
        if (opened[row][col]) {
            return;
        }

        int flatLoc = flattenLocation(row, col);
        int[] neighborRows = new int[]{row, row, row + 1, row - 1};
        int[] neighborCols = new int[]{col - 1, col + 1, col, col};
        for (int i = 0; i < 4; i++) {
            int neighborRow = neighborRows[i];
            int neighborCol = neighborCols[i];

            if (isValidLocation(neighborRow, neighborCol) && opened[neighborRow][neighborCol]) {
                int locNeighbor = flattenLocation(neighborRow, neighborCol);
                unionFind.union(flatLoc, locNeighbor);
                unionFindFull.union(flatLoc, locNeighbor);
            }
        }
        // Connect sites if they are in the first or last row
        // Connecting here instead of pre-connecting in constructor is because when N=1, pre-connection leads the sites
        // to be percolated during creation.
        if (row == 0) {
            unionFind.union(flatLoc, posVirtualTop);
            unionFindFull.union(flatLoc, posVirtualTop);
        }
        if (row == sideLength - 1) {
            unionFind.union(flatLoc, posVirtualBtm);
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
        return isOpen(row, col) && unionFindFull.connected(flattenLocation(row, col), posVirtualTop);
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
