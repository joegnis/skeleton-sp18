package read31;

public class Sort {
    /** Sorts strings destructively. */
    public static void sort(String[] x) {
        sortHelper(x, 0, x.length);
    }

    private static void sortHelper(String[] x, int start, int end) {
        // find the smallest item
        // move it to the front
        // selection sort the rest (using recursion?)
        if (start == end - 1) {
            return;
        }
        String smallest = x[start];
        int indexSmallest = start;
        for (int i = start; i < end; i++) {
            String str = x[i];
            if (str.compareTo(smallest) < 0) {
                smallest = str;
                indexSmallest = i;
            }
        }
        String temp = x[start];
        x[start] = smallest;
        x[indexSmallest] = temp;

        sortHelper(x, start + 1, end);
    }
}
