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
        Field fieldUsedStateSkips = solver.getClass().getDeclaredField("countSkipsUsedState");
        Field fieldEqualsToGrandparentSkips = solver.getClass().getDeclaredField("countSkipsEqualToGrandparent");
        Field fieldBothSkips = solver.getClass().getDeclaredField("countSkipsBothUsedStateAndEqualToGrandparent");
        fieldInsertCount.setAccessible(true);
        fieldCacheHitCount.setAccessible(true);
        fieldUsedStateSkips.setAccessible(true);
        fieldEqualsToGrandparentSkips.setAccessible(true);
        fieldBothSkips.setAccessible(true);
        int totalInsertToFringe = fieldInsertCount.getInt(solver);
        int countCacheHits = fieldCacheHitCount.getInt(solver);
        int countUsedStateSkip = fieldUsedStateSkips.getInt(solver);
        int countEqualsToGrandparentSkips = fieldEqualsToGrandparentSkips.getInt(solver);
        int countBothSkips = fieldBothSkips.getInt(solver);
        StdOut.printf("Number of total insertions into fringe: %d%n", totalInsertToFringe);
        StdOut.printf("Number of cache hits: %d%n", countCacheHits);
        StdOut.printf("Number of skips due to state already used: %d%n", countUsedStateSkip);
        StdOut.printf("Number of skips due to equality to grandparent: %d%n", countEqualsToGrandparentSkips);
        StdOut.printf("Number of skips due to both reason above: %d%n", countBothSkips);
    }
}
