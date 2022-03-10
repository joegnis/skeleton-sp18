package hw4.puzzle;

import edu.princeton.cs.algs4.StdOut;

import java.lang.reflect.Field;

import static hw4.puzzle.TestSolver.readBoard;

public class EightPuzzleSolver {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        WorldState initial = readBoard(args[0]);
        Solver solver = new Solver(initial);
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (WorldState ws : solver.solution()) {
            StdOut.println(ws);
        }

        Field fieldInsertCount = solver.getClass().getDeclaredField("totalInsertToFringe");
        Field fieldCacheHitCount = solver.getClass().getDeclaredField("countCacheHits");
        fieldInsertCount.setAccessible(true);
        fieldCacheHitCount.setAccessible(true);
        int totalInsertToFringe = fieldInsertCount.getInt(solver);
        int countCacheHits = fieldCacheHitCount.getInt(solver);
        StdOut.printf("Number of total insertions into fringe: %d%n", totalInsertToFringe);
        StdOut.printf("Number of cache hits: %d%n", countCacheHits);
    }
}
