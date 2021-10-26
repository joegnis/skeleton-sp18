import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque<Character> d = palindrome.wordToDeque("persiflage");
        StringBuilder actual = new StringBuilder();
        for (int i = 0; i < "persiflage".length(); i++) {
            actual.append(d.removeFirst());
        }
        assertEquals("persiflage", actual.toString());
    }

    @TestFactory
    public Stream<DynamicTest> testIsPalindrome() {
        Map<String, Boolean> expected = Map.ofEntries(
                Map.entry("aka", true),
                Map.entry("a", true),
                Map.entry("cat", false),
                Map.entry("", true),
                Map.entry("Aka", false),
                Map.entry("AkA", true),
                Map.entry("degged", true),
                Map.entry("DeggeD", true),
                Map.entry("kakkak", true)
        );

        return expected.entrySet().stream()
                .map(entry -> DynamicTest.dynamicTest("If " + entry.getKey() + " is palindrome",
                        () -> assertEquals(entry.getValue(), palindrome.isPalindrome(entry.getKey()))));
    }

    @Test
    public void testIsPalindromeNull() {
        assertFalse(palindrome.isPalindrome(null));
    }
}
