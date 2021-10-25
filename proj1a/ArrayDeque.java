/**
 * Double ended deque implemented by array
 *
 * Time complexity:
 * - {@link #addFirst(T)}, {@link #addLast(T)}, {@link #removeLast()}, {@link #removeLast()} take constant time,
 *   except during resizing operations
 * - get, size take constant time
 * Other details
 * - The starting capacity of the array is 8.
 * - The amount of memory used at any given time is proportional to the number of items.
 * - For arrays of length 16 or more, usage factor is at least 25%. For smaller arrays, usage factor is arbitrarily low.
 * - The array is circular.
 */
public class ArrayDeque<T> implements Deque<T> {
    private final static int RESIZE_MULTIPLIER = 2;

    private T[] items;
    private int size;
    private int capacity;
    private int firstIndex;
    private int lastIndex;

    public ArrayDeque() {
        capacity = 8;
        items = (T[]) new Object[capacity];
        size = 0;
        firstIndex = 0;
        lastIndex = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {

    }

    @Override
    public void addFirst(T item) {

    }

    @Override
    public void addLast(T item) {

    }

    @Override
    public T removeFirst() {
        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T get(int index) {
        return null;
    }

    /**
     * Returns an index's position in the circular array
     *
     * @param pos original position
     * @param offset offset to the origin
     * @return new position
     */
    private int offsetPos(int pos, int offset) {
        int newPos = pos + offset;
        while (newPos < 0) {
            newPos += capacity;
        }
        while (newPos >= capacity) {
            newPos -= capacity;
        }
        return newPos;
    }

    private void resize() {
        double usageRatio = size / (double) capacity;
    }
}
