/**
 * Double ended queue
 *
 * Space complexity:
 * The amount of memory used at any given time is proportional to the number of items. For example, if you add 10,000
 * items to the deque, and then remove 9,999 items, the resulting size should be more like a deque with 1 item
 * than 10,000. Does not maintain references to items that are no longer in the deque.
 *
 * Time complexity:
 * - {@link #addFirst(T)}, {@link #addLast(T)}, {@link #removeFirst()}, and {@link #removeLast()} take constant time.
 * - {@link #size()} takes constant time
 * - {@link #get(int)} takes linear time
 *
 * Does not use Java’s built in LinkedList data structure (or any data structure from java.util.*)
 */
public class LinkedListDeque<T> implements Deque<T> {
    public LinkedListDeque() {
    }

    public boolean isEmpty() {
        return false;
    }

    /**
     * Returns the number of items in the deque.
     *
     * Takes constant time.
     */
    public int size() {
        return 0;
    }

    public void printDeque() {

    }

    /**
     * Adds an item of type T to the front of the deque.
     *
     * Takes constant time.
     */
    public void addFirst(T item) {

    }

    /**
     * Adds an item of type T to the back of the deque.
     *
     * Takes constant time
     */
    public void addLast(T item) {

    }

    /**
     * Removes and returns the item at the front of the deque.
     *
     * If no such item exists, returns null.
     * Takes constant time.
     */
    public T removeFirst() {
        return null;
    }

    /**
     * Removes and returns the item at the back of the deque.
     *
     * If no such item exists, returns null.
     * Takes constant time.
     */
    public T removeLast() {
        return null;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *
     * If no such item exists, returns null. Must not alter the deque!
     * Takes linear time. Implemented iteratively.
     */
    public T get(int index) {
        return null;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *
     * If no such item exists, returns null. Must not alter the deque!
     * Takes linear time. Implemented recursively.
     */
    public T getRecursive(int index) {
        return null;
    }
}
