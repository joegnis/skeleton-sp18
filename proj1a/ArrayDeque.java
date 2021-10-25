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
    private int sentinel;
    private int last;  // pos of last element + 1

    public ArrayDeque() {
        capacity = 8;
        items = (T[]) new Object[capacity];
        size = 0;
        sentinel = capacity - 1;
        last = 0;
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
        StringBuilder builder = new StringBuilder();
        for (int index = offsetPos(sentinel, 1); index < last; index = offsetPos(index, 1)) {
            builder.append(items[index]);
            builder.append(" ");
        }
        if (builder.length() > 0) {
            builder.deleteCharAt(builder.length() - 1);
        }
        System.out.print(builder);
    }

    @Override
    public void addFirst(T item) {
        items[sentinel] = item;
        sentinel = offsetPos(sentinel, -1);
        size += 1;
    }

    @Override
    public void addLast(T item) {
        items[last] = item;
        last = offsetPos(last, 1);
        size += 1;
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

    public T[] toArray() {
        T[] array = (T []) new Object[size];

        int arrayPos = 0;
        for (int pos = offsetPos(sentinel, 1); arrayPos < size; pos = offsetPos(pos, 1), arrayPos += 1) {
            // arrayPos < size detects empty items
            array[arrayPos] = items[pos];
        }

        return array;
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
