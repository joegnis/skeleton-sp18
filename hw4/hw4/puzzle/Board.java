package hw4.puzzle;

import java.util.*;

public class Board implements WorldState {
    private final int N;
    private final int[][] tiles;

    /**
     * Constructs a board from an N-by-N array of tiles where
     * tiles[i][j] = tile at row i, column j
     */
    public Board(int[][] tiles) {
        N = tiles.length;
        this.tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            this.tiles[i] = Arrays.copyOf(tiles[i], N);
        }
    }

    /**
     * Returns value of tile at row i, column j (or 0 if blank)
     */
    public int tileAt(int i, int j) {
        if (!isInBound(i, j)) {
            throw new IndexOutOfBoundsException();
        }
        return tiles[i][j];
    }

    /**
     * Returns the board size N
     */
    public int size() {
        return N;
    }

    /**
     * Returns the neighbors of the current board
     */
    public Iterable<WorldState> neighbors() {
        int zeroRow = -1;
        int zeroCol = -1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
            }
        }
        if (zeroRow == -1) {
            throw new IllegalArgumentException();
        }
        int[] neighborRows = new int[]{zeroRow, zeroRow - 1, zeroRow, zeroRow + 1};
        int[] neighborCols = new int[]{zeroCol - 1, zeroCol, zeroCol + 1, zeroCol};
        List<WorldState> results = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            int nextRow = neighborRows[i];
            int nextCol = neighborCols[i];
            if (isInBound(nextRow, nextCol)) {
                int[][] nextTiles = new int[N][N];
                for (int row = 0; row < N; row++) {
                    nextTiles[row] = Arrays.copyOf(tiles[row], N);
                }
                nextTiles[zeroRow][zeroCol] = nextTiles[nextRow][nextCol];
                nextTiles[nextRow][nextCol] = 0;
                results.add(new Board(nextTiles));
            }
        }

        return results;
    }

    /**
     * Hamming estimate
     */
    public int hamming() {
        int distance = 0;
        int rightTile = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int tile = tiles[i][j];
                if (tile == 0) {
                    // The empty cell does not count
                    continue;
                }
                if (rightTile == tile) {
                    distance++;
                }
                rightTile++;
            }
        }
        return distance;
    }

    /**
     * Manhattan estimate
     */
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int tile = tiles[i][j];
                if (tile == 0) {
                    // The empty cell does not count
                    continue;
                }
                int tileRightRow = (tile - 1) / N;
                int tileRightCol = (tile - 1) % N;
                distance += Math.abs(tileRightRow - i) + Math.abs(tileRightCol - j);
            }
        }
        return distance;
    }

    /**
     * Estimated distance to goal. This method should
     * simply return the results of manhattan() when submitted to
     * Gradescope.
     */
    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    /**
     * Returns true if this board's tile values are the same
     * position as y's
     */
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }

        Board other = (Board) y;
        if (N != other.tiles.length) {
            return false;
        }
        for (int i = 0; i < N; i++) {
            if (!Arrays.equals(tiles[i], other.tiles[i])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(tiles);
    }

    /**
     * Returns the string representation of the board.
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(String.format("{N=%d, board={", N));
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append(", ");
        }
        s.append("}}");
        return s.toString();
    }

    private boolean isInBound(int row, int col) {
        return !(row < 0 || row >= N || col < 0 || col >= N);
    }
}
