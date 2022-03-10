package hw4.puzzle;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static hw4.puzzle.TestSolver.readBoard;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSolverJunit5 {
    @TestFactory
    public Stream<DynamicTest> test3x3BoardPuzzles() {
        return IntStream.rangeClosed(0, 30).mapToObj(numMoves -> {
            String puzzleFilename = String.format("input/puzzle3x3-%02d.txt", numMoves);
            return DynamicTest.dynamicTest(String.format("%s, %d", puzzleFilename, numMoves),
                    () -> {
                        WorldState board = readBoard(puzzleFilename);
                        Solver solver = new Solver(board);
                        assertEquals(numMoves, solver.moves());
                    }
            );
        });
    }

    @TestFactory
    public Stream<DynamicTest> test2x2BoardPuzzles() {
        return IntStream.rangeClosed(0, 6).mapToObj(numMoves -> {
            String puzzleFilename = String.format("input/puzzle2x2-%02d.txt", numMoves);
            return DynamicTest.dynamicTest(String.format("%s, %d", puzzleFilename, numMoves),
                    () -> {
                        WorldState board = readBoard(puzzleFilename);
                        Solver solver = new Solver(board);
                        assertEquals(numMoves, solver.moves());
                    }
            );
        });
    }

    @TestFactory
    public Stream<DynamicTest> test4x4BoardPuzzles() {
        return IntStream.rangeClosed(0, 30).mapToObj(numMoves -> {
            String puzzleFilename = String.format("input/puzzle4x4-%02d.txt", numMoves);
            return DynamicTest.dynamicTest(String.format("%s, %d", puzzleFilename, numMoves),
                    () -> {
                        WorldState board = readBoard(puzzleFilename);
                        Solver solver = new Solver(board);
                        assertEquals(numMoves, solver.moves());
                    }
            );
        });
    }

    @TestFactory
    public Stream<DynamicTest> testVariousBoardPuzzles() {
        return IntStream.rangeClosed(0, 31).mapToObj(numMoves -> {
            String puzzleFilename = String.format("input/puzzle%02d.txt", numMoves);
            return DynamicTest.dynamicTest(String.format("%s, %d", puzzleFilename, numMoves),
                    () -> {
                        WorldState board = readBoard(puzzleFilename);
                        Solver solver = new Solver(board);
                        assertEquals(numMoves, solver.moves());
                    }
            );
        });
    }
}
