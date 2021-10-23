/**
 * Double ended queue implemented by a linked list
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
    private static class Node<T> {
        public T item;
        public Node<T> next;
        public Node<T> prev;

        public Node(T item, Node<T> next, Node<T> prev) {
            this.item = item;
            this.next = next;
            this.prev = prev;
        }

        public Node(T item) {
            this.item = item;
            this.next = null;
            this.prev = null;
        }
    }
    private int size;
    private final Node<T> sentinel;

    public LinkedListDeque() {
        size = 0;
        sentinel = new Node<>(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the deque.
     *
     * Takes constant time.
     */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     */
    public void printDeque() {

    }

    /**
     * Adds an item of type T to the front of the deque.
     *
     * Takes constant time.
     */
    public void addFirst(T item) {
        Node<T> newNode = new Node<>(item, sentinel.next, sentinel);
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        size += 1;
    }

    /**
     * Adds an item of type T to the back of the deque.
     *
     * Takes constant time
     */
    public void addLast(T item) {
        Node<T> newNode = new Node<>(item, sentinel, sentinel.prev);
        sentinel.prev.next = newNode;
        sentinel.prev = newNode;
        size += 1;
    }

    /**
     * Removes and returns the item at the front of the deque.
     *
     * If no such item exists, returns null.
     * Takes constant time.
     */
    public T removeFirst() {
        Node<T> firstNode = sentinel.next;
        sentinel.next = firstNode.next;
        firstNode.next.prev = sentinel;

        if (firstNode != sentinel) {
            firstNode.next = null;
            firstNode.prev = null;
            size -= 1;
        }
        return firstNode.item;
    }

    /**
     * Removes and returns the item at the back of the deque.
     *
     * If no such item exists, returns null.
     * Takes constant time.
     */
    public T removeLast() {
        Node<T> lastNode = sentinel.prev;
        lastNode.prev.next = sentinel;
        sentinel.prev = lastNode.prev;

        if (lastNode != sentinel) {
            lastNode.prev = null;
            lastNode.next = null;
            size -= 1;
        }
        return lastNode.item;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *
     * If no such item exists, returns null. Must not alter the deque!
     * Takes linear time. Implemented iteratively.
     */
    public T get(int index) {
        int curIndex = 0;
        Node<T> curNode = sentinel.next;
        while (curIndex < index && curNode != sentinel) {
            curNode = curNode.next;
            curIndex += 1;
        }
        if (curNode == sentinel || index < 0) {
            return null;
        } else {
            return curNode.item;
        }
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     *
     * If no such item exists, returns null. Must not alter the deque!
     * Takes linear time. Implemented recursively.
     */
    public T getRecursive(int index) {
        if (index < 0) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }

    private T getRecursiveHelper(int index, Node<T> curNode) {
        if (curNode == sentinel) {
            return null;
        } else if (index == 0) {
            return curNode.item;
        }
        return getRecursiveHelper(index - 1, curNode.next);
    }

    public T[] toArray() {
        T[] array = (T []) new Object[size];

        int curIndex = 0;
        for (Node<T> curNode = sentinel.next; curNode != sentinel; curNode = curNode.next, curIndex += 1) {
            array[curIndex] = curNode.item;
        }

        return array;
    }
}
