import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class TestOffByN {

    @Test
    public void testIsPalindrome() {
        Palindrome palindrome = new Palindrome();
        List<Integer> offBys = Arrays.asList(
                0, 0, 0, 1, 1, 1, 5, 5, 5, 4, 4, 3, 3
        );
        List<String> words = Arrays.asList(
                "aka", "quart", "a",
                "flake", "sheep", "b",
                "tiffany", "phloem", "",
                "wages", "tiny",
                "prius", "ionic"
        );
        List<Boolean> isPalindromes = Arrays.asList(
                true, false, true,
                true, false, true,
                true, false, true,
                true, false,
                true, false
        );

        for (String word : words) {
            int index = words.indexOf(word);
            boolean isPalindrome = isPalindromes.get(index);
            int offBy = offBys.get(index);
            assertEquals(isPalindrome, palindrome.isPalindrome(word, new OffByN(offBy)));
        }
    }
}
