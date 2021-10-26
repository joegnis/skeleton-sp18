public class Palindrome {
    /**
     * Returns a Deque where the characters appear in the same order as in the String
     */
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        word.chars().forEachOrdered((int ch) -> deque.addLast((char) ch));
        return deque;
    }

    /**
     * Returns true if the given word is a palindrome, and false otherwise
     */
    public boolean isPalindrome(String word) {
        if (word == null) {
            return false;
        }
        return isPalindromeHelper(wordToDeque(word));
    }

    private boolean isPalindromeHelper(Deque<Character> wordDeque) {
        if (wordDeque.size() == 0 || wordDeque.size() == 1) {
            return true;
        }

        if (wordDeque.removeFirst() == wordDeque.removeLast()) {
            return isPalindromeHelper(wordDeque);
        } else {
            return false;
        }
    }
}
