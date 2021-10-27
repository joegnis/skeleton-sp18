import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @TestFactory
    public Stream<DynamicTest> testEqualChars() {
        List<Character> firstChars = Arrays.asList('a', 'b', 'c', 'z', 'b');
        List<Character> secondChars = Arrays.asList('b', 'a', 'c', 'a', 'g');
        List<Boolean> expected = Arrays.asList(true, true, false, false, false);

        return firstChars.stream()
                .map(firstChar -> DynamicTest.dynamicTest(
                        String.format("If %c and %c are off by one",
                                firstChar, secondChars.get(firstChars.indexOf(firstChar))),
                        () -> {
                            int index = firstChars.indexOf(firstChar);
                            assertEquals(
                                    expected.get(index),
                                    offByOne.equalChars(firstChar, secondChars.get(index)));
                        }));
    }

    @TestFactory
    public Stream<DynamicTest> testIsPalindromeCC() {
        Palindrome palindrome = new Palindrome();
        Map<String, Boolean> expected = Map.ofEntries(
                Map.entry("aka", false),
                Map.entry("a", true),
                Map.entry("cat", false),
                Map.entry("", true),
                Map.entry("Aka", false),
                Map.entry("AkA", false),
                Map.entry("degged", false),
                Map.entry("DeggeD", false),
                Map.entry("kakkak", false),
                Map.entry("flake", true),
                Map.entry("Flake", false),
                Map.entry("ab", true),
                Map.entry("Ab", false),
                Map.entry("&%", true)
        );

        return expected.entrySet().stream()
                .map(entry -> DynamicTest.dynamicTest("If " + entry.getKey() + " is palindrome",
                        () -> assertEquals(entry.getValue(), palindrome.isPalindrome(entry.getKey(), offByOne))));
    }

    @Test
    public void testIsPalindromeNullCC() {
        Palindrome palindrome = new Palindrome();
        assertFalse(palindrome.isPalindrome(null, offByOne));
    }
}
