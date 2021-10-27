import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestOffByN {

    private Palindrome palindrome;

    @BeforeEach
    public void setUpEach() {
        palindrome = new Palindrome();
    }

    @TestFactory
    public Stream<DynamicTest> testIsPalindrome() {
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
        return words.stream()
                .map(word -> {
                    int index = words.indexOf(word);
                    boolean isPalindrome = isPalindromes.get(index);
                    int offBy = offBys.get(index);
                    return DynamicTest.dynamicTest(
                            String.format("Is %s off by %d palindrome", word, offBy),
                            () -> assertEquals(isPalindrome, palindrome.isPalindrome(word, new OffByN(offBy))));
                });
    }
}
