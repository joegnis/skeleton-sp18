import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    @Test
    public void testEqualChars() {
        List<Character> firstChars = Arrays.asList('a', 'b', 'c', 'z', 'b');
        List<Character> secondChars = Arrays.asList('b', 'a', 'c', 'a', 'g');
        List<Boolean> expected = Arrays.asList(true, true, false, false, false);

        for (char firstChar : firstChars) {

            int index = firstChars.indexOf(firstChar);
            assertEquals(
                    expected.get(index),
                    offByOne.equalChars(firstChar, secondChars.get(index))
            );
        }
    }

    @Test
    public void testIsPalindromeCC() {
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

        for (var entry : expected.entrySet()) {
            assertEquals(entry.getValue(), palindrome.isPalindrome(entry.getKey(), offByOne));
        }
    }

    @Test
    public void testIsPalindromeNullCC() {
        Palindrome palindrome = new Palindrome();
        assertFalse(palindrome.isPalindrome(null, offByOne));
    }
}
