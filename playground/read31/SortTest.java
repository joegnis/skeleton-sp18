package read31;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;


public class SortTest {

    @Test
    public void testSort() {
        String[] strArr = new String[]{"free", "change", "edit", "apple"};
        Sort.sort(strArr);
        assertArrayEquals(new String[]{"apple", "change", "edit", "free"}, strArr);
    }
}
