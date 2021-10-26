/**
 * Double ended deque implemented by array
 * <p>
 * Time complexity:
 * - {@link #addFirst(T)}, {@link #addLast(T)}, {@link #removeLast()}, {@link #removeLast()} take constant time,
 * except during resizing operations
 * - get, size take constant time
 * Other details
 * - The starting capacity of the array is 8.
 * - The amount of memory used at any given time is proportional to the number of items.
 * - For arrays of length 16 or more, usage factor is at least 25%. For smaller arrays, usage factor is arbitrarily low.
 * - The array is circular.
 */
public class ArrayDeque<T> implements Deque<T> {
    private static final int RESIZE_MULTIPLIER = 2;
    private static final double USAGE_RATIO_SHRINK_THRESHOLD = 0.25;
    private static final int USAGE_RATIO_MIN_CAPACITY = 16;

    private T[] items;
    private int size;
    private int capacity;
    private int sentinel;
    private int last;  // pos of last element + 1

    public ArrayDeque() {
        this(8);
    }

    public ArrayDeque(int capacity) {
        this.capacity = capacity;
        items = (T[]) new Object[this.capacity];
        size = 0;
        sentinel = capacity - 1;
        last = 0;
    }

    public static <T> ArrayDeque<T> of(T... values) {
        int numValues = values.length;
        int capacity = 8;
        while (capacity <= numValues + 1) {
            capacity *= RESIZE_MULTIPLIER;
        }
        ArrayDeque<T> deque = new ArrayDeque<>(capacity);
        System.arraycopy(values, 0, deque.items, 0, numValues);
        deque.size = numValues;
        deque.last = deque.offsetPos(numValues - 1, 1);
        return deque;
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
        for (int index = offsetPos(sentinel, 1); index != last; index = offsetPos(index, 1)) {
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
        resize(true);
    }

    @Override
    public void addLast(T item) {
        items[last] = item;
        last = offsetPos(last, 1);
        resize(true);
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        sentinel = offsetPos(sentinel, 1);
        T removed = items[sentinel];
        resize(false);
        return removed;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        last = offsetPos(last, -1);
        T removed = items[last];
        resize(false);
        return removed;
    }

    @Override
    public T get(int pos) {
        if (pos < 0 || pos >= size) {
            return null;
        }
        return items[offsetPos(sentinel, 1 + pos)];
    }

    public T[] toArray() {
        T[] array = (T[]) new Object[size];

        int arrayPos = 0;
        for (int index = offsetPos(sentinel, 1); index != last; index = offsetPos(index, 1)) {
            // can not use index < last as exit condition because it is a circular array where last can be
            // less than index in the beginning
            array[arrayPos] = items[index];
            arrayPos += 1;
        }

        return array;
    }

    /**
     * Returns an index's position in the circular array
     *
     * @param pos    original position
     * @param offset offset to the origin
     * @return new position
     */
    private int offsetPos(int pos, int offset) {
        int newPos = (pos + offset) % capacity;
        if (newPos < 0) {
            newPos += capacity;
        }
        return newPos;
    }

    /**
     * Resizes items array, and expand/shrink accordingly
     * <p>
     * Called after adding/removing an item
     */
    private void resize(boolean incSizeByOne) {
        size += (incSizeByOne ? 1 : -1);
        double usageRatio = size / (double) capacity;
        T[] newItems = items;
        if (size == capacity - 1) {
            // Expanding
            // Chooses the threshold as capacity - 1 to avoid considering too many conditions
            int newCapacity = capacity * RESIZE_MULTIPLIER;
            newItems = (T[]) new Object[newCapacity];
            int posFirstItem = offsetPos(sentinel, 1);
            if (posFirstItem < last) {
                System.arraycopy(items, posFirstItem, newItems, posFirstItem, last - posFirstItem);
                sentinel = newCapacity - 1;
            } else {
                int firstPartLen = capacity - posFirstItem;
                System.arraycopy(items, posFirstItem, newItems, newCapacity - firstPartLen, firstPartLen);
                System.arraycopy(items, 0, newItems, 0, last);
                sentinel = newCapacity - firstPartLen - 1;
            }
            capacity = newCapacity;
        } else if (capacity >= USAGE_RATIO_MIN_CAPACITY && usageRatio < USAGE_RATIO_SHRINK_THRESHOLD) {
            // Shrinking
            int newCapacity = capacity / RESIZE_MULTIPLIER;
            newItems = (T[]) new Object[newCapacity];
            int posFirstItem = offsetPos(sentinel, 1);
            if (posFirstItem < last) {
                // Copys to the start of new items
                System.arraycopy(items, posFirstItem, newItems, 0, last - posFirstItem);
                sentinel = newCapacity - 1;
                last = last - posFirstItem;
            } else {
                System.arraycopy(items, posFirstItem, newItems, 0, capacity - posFirstItem);
                System.arraycopy(items, 0, newItems, capacity - posFirstItem, last);
                sentinel = newCapacity - 1;
                last = capacity - posFirstItem + last;
            }
            capacity = newCapacity;
        }
        items = newItems;
    }
}
