public class Palindrome {
    /**
     * Returns a Deque where the characters appear in the same order as in the String
     */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        word.chars().forEachOrdered((int ch) -> deque.addLast((char) ch));
        return deque;
    }
}
