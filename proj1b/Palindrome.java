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

    /**
     * Returns true if the word is a palindrome according to the character comparison test provided
     */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) {
            return false;
        }
        return isPalindromeHelper(wordToDeque(word), cc);
    }

    private boolean isPalindromeHelper(Deque<Character> wordDeque, CharacterComparator cc) {
        if (wordDeque.size() == 0 || wordDeque.size() == 1) {
            return true;
        }

        if (cc.equalChars(wordDeque.removeFirst(), wordDeque.removeLast())) {
            return isPalindromeHelper(wordDeque, cc);
        } else {
            return false;
        }
    }

}
