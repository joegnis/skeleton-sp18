import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class IntListTest {

    /**
     * Example test that verifies correctness of the IntList.of static
     * method. The main point of this is to convince you that
     * assertEquals knows how to handle IntLists just fine.
     */

    @Test
    public void testList() {
        IntList one = new IntList(1, null);
        IntList twoOne = new IntList(2, one);
        IntList threeTwoOne = new IntList(3, twoOne);

        IntList x = IntList.of(3, 2, 1);
        assertEquals(threeTwoOne, x);
    }

    @Test
    public void testdSquareList() {
        IntList L = IntList.of(1, 2, 3);
        IntList.dSquareList(L);
        assertEquals(IntList.of(1, 4, 9), L);
    }

    /**
     * Do not use the new keyword in your tests. You can create
     * lists using the handy IntList.of method.
     * <p>
     * Make sure to include test cases involving lists of various sizes
     * on both sides of the operation. That includes the empty of, which
     * can be instantiated, for example, with
     * IntList empty = IntList.of().
     * <p>
     * Keep in mind that dcatenate(A, B) is NOT required to leave A untouched.
     * Anything can happen to A.
     */

    @Test
    public void testSquareListRecursive() {
        IntList L = IntList.of(1, 2, 3);
        IntList res = IntList.squareListRecursive(L);
        assertEquals(IntList.of(1, 2, 3), L);
        assertEquals(IntList.of(1, 4, 9), res);
    }

    @Test
    public void testSize() {
        assertEquals(1, Objects.requireNonNull(IntList.of(1)).size());
        assertEquals(2, Objects.requireNonNull(IntList.of(1, 2)).size());
        assertEquals(5, Objects.requireNonNull(IntList.of(1, 2, 3, 4, 5)).size());
    }

    @Test
    public void testIterativeSize() {
        assertEquals(1, Objects.requireNonNull(IntList.of(1)).iterativeSize());
        assertEquals(2, Objects.requireNonNull(IntList.of(1, 2)).iterativeSize());
        assertEquals(5, Objects.requireNonNull(IntList.of(1, 2, 3, 4, 5)).iterativeSize());
    }

    @Test
    public void testIterativeReversed() {
        assertEquals(IntList.of(1), Objects.requireNonNull(IntList.of(1)).iterativeReversed());
        assertEquals(IntList.of(1, 2), Objects.requireNonNull(IntList.of(2, 1)).iterativeReversed());
        assertEquals(IntList.of(3, 4, 5, 1), Objects.requireNonNull(IntList.of(1, 5, 4, 3)).iterativeReversed());
    }

    @Test
    public void testReversed() {
        assertEquals(IntList.of(1), Objects.requireNonNull(IntList.of(1)).reversed());
        assertEquals(IntList.of(1, 2), Objects.requireNonNull(IntList.of(2, 1)).reversed());
        assertEquals(IntList.of(3, 4, 5, 1), Objects.requireNonNull(IntList.of(1, 5, 4, 3)).reversed());
        assertEquals(IntList.of(3, 2, 4, 5, 1), Objects.requireNonNull(IntList.of(1, 5, 4, 2, 3)).reversed());
    }

    @TestFactory
    public Stream<DynamicTest> testIterativeReversedInPlace() {
        List<IntList> inputLists = Arrays.asList(
                IntList.of(1),
                IntList.of(3, 5),
                IntList.of(3, 4, 5, 1),
                IntList.of(1, 6, 8, 2, 0)
        );
        List<IntList> expectedOutputs = Arrays.asList(
                IntList.of(1),
                IntList.of(5, 3),
                IntList.of(1, 5, 4, 3),
                IntList.of(0, 2, 8, 6, 1)
        );

        return inputLists.stream()
                        .map(input -> DynamicTest.dynamicTest("Reversing " + input,
                                () -> {
                            IntList expectedOutput = expectedOutputs.get(inputLists.indexOf(input));
                            IntList reversed = input.iterativeReversedInPlace();
                            int inputSize = input.size();
                            assertEquals(expectedOutput, reversed);
                            if (inputSize > 1) {
                                // Tests if reversed in place
                                assertNotEquals(expectedOutput, input);
                            }
                        }));
    }

    @TestFactory
    public Stream<DynamicTest> testReversedInPlace() {
        List<IntList> inputLists = Arrays.asList(
                IntList.of(1),
                IntList.of(3, 5),
                IntList.of(3, 4, 5, 1),
                IntList.of(1, 6, 8, 2, 0)
        );
        List<IntList> expectedOutputs = Arrays.asList(
                IntList.of(1),
                IntList.of(5, 3),
                IntList.of(1, 5, 4, 3),
                IntList.of(0, 2, 8, 6, 1)
        );

        return inputLists.stream()
                .map(input -> DynamicTest.dynamicTest("Reversing " + input,
                        () -> {
                            IntList expectedOutput = expectedOutputs.get(inputLists.indexOf(input));
                            IntList reversed = input.reversedInPlace();
                            int inputSize = input.size();
                            assertEquals(expectedOutput, reversed);
                            if (inputSize > 1) {
                                // Tests if reversed in place
                                assertNotEquals(expectedOutput, input);
                            }
                        }));
    }

    @TestFactory
    public Stream<DynamicTest> testGetIth() {
        IntList tested = IntList.of(5, 6, -2, 4, 8);
        List<Integer> inputs = Arrays.asList(0, 1, 2, 3, 4);
        List<Integer> outputs = Arrays.asList(5, 6, -2, 4, 8);

        Objects.requireNonNull(tested);
        return inputs.stream()
                .map(input -> DynamicTest.dynamicTest("Getting element at location " + input,
                        () -> {
                            int expected = outputs.get(inputs.indexOf(input));
                            assertEquals(expected, tested.get(input));
                        }));
    }

    @Test
    public void testDcatenate() {
        IntList A = IntList.of(1, 2, 3);
        IntList B = IntList.of(4, 5, 6);
        IntList exp = IntList.of(1, 2, 3, 4, 5, 6);
        assertEquals(exp, IntList.dcatenate(A, B));
        assertEquals(IntList.of(1, 2, 3, 4, 5, 6), A);
    }

    @Test
    public void testDcatendateNull() {
        assertEquals(IntList.of(1, 2, 3), IntList.dcatenate(null, IntList.of(1, 2, 3)));
        assertEquals(IntList.of(1, 2, 3), IntList.dcatenate(IntList.of(1, 2, 3), null));
    }

    @Test
    public void testCatenate() {
        IntList A = IntList.of(1, 2, 3);
        IntList B = IntList.of(4, 5, 6);
        IntList exp = IntList.of(1, 2, 3, 4, 5, 6);
        assertEquals(exp, IntList.catenate(A, B));
        assertEquals(IntList.of(1, 2, 3), A);
    }

    @Test
    public void testCatendateNull() {
        IntList A = IntList.of(1, 2, 3);
        assertEquals(A, IntList.dcatenate(null, A));
        assertEquals(A, IntList.dcatenate(A, null));
    }
}
