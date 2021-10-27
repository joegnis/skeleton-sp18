public class OffByOne implements CharacterComparator {
    /**
     * Returns true for characters that are different by exactly one
     */
    @Override
    public boolean equalChars(char x, char y) {
        int difference = x - y;
        return difference == 1 || difference == -1;
    }
}
