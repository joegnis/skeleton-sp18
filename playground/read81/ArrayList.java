package read81;

import java.util.AbstractList;
import java.util.Collection;
import java.util.LinkedList;

/**
 * An array-based list implementation copied from Project 1A
 */
public class ArrayList<T> extends AbstractList<T> {
    private static final int RESIZE_MULTIPLIER = 2;
    private static final double USAGE_RATIO_SHRINK_THRESHOLD = 0.25;
    private static final int USAGE_RATIO_MIN_CAPACITY = 16;

    private T[] items;
    private int size;
    private int capacity;
    private int sentinel;
    private int last;  // pos of last element + 1

    ArrayList() {
        this(8);
    }

    ArrayList(Collection<? extends T> other) {
        int numValues = other.size();
        int capacity = 8;
        while (capacity <= numValues + 1) {
            capacity *= RESIZE_MULTIPLIER;
        }
        this.capacity = capacity;
        @SuppressWarnings("unchecked") T[] at = (T[]) new Object[this.capacity];
        items = at;
        sentinel = capacity - 1;

        int i = 0;
        for (T o : other) {
            items[i] = o;
            i += 1;
        }
        size = numValues;
        last = offsetPos(numValues - 1, 1);
    }

    public static <T> ArrayList<T> of(T... values) {
        int numValues = values.length;
        int capacity = 8;
        while (capacity <= numValues + 1) {
            capacity *= RESIZE_MULTIPLIER;
        }
        ArrayList<T> aList = new ArrayList<>(capacity);
        System.arraycopy(values, 0, aList.items, 0, numValues);
        aList.size = numValues;
        aList.last = aList.offsetPos(numValues - 1, 1);
        return aList;
    }

    private ArrayList(int capacity) {
        this.capacity = capacity;
        @SuppressWarnings("unchecked") T[] at = (T[]) new Object[this.capacity];
        items = at;
        size = 0;
        sentinel = capacity - 1;
        last = 0;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        return items[offsetPos(sentinel, 1 + index)];
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
    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        int realIndex = offsetPos(sentinel, 1 + index);
        T old = items[realIndex];
        items[realIndex] = element;
        return old;
    }

    @Override
    public boolean add(T t) {
        add(size, t);
        return true;
    }

    @Override
    public void add(int index, T element) {
        if (index == 0) {
            items[sentinel] = element;
            sentinel = offsetPos(sentinel, -1);
            resize(true);
        } else if (index == size) {
            items[last] = element;
            last = offsetPos(last, 1);
            resize(true);
        }
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (index == size - 1) {
            last = offsetPos(last, -1);
            T removed = items[last];
            resize(false);
            return removed;
        } else if (index == 0) {
            sentinel = offsetPos(sentinel, 1);
            T removed = items[sentinel];
            resize(false);
            return removed;
        }
        return null;
    }

    @Override
    public Object[] toArray() {
        @SuppressWarnings("unchecked")
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
        if (size == capacity - 1) {
            // Expanding
            // Chooses the threshold as capacity - 1 to avoid considering too many conditions
            int newCapacity = capacity * RESIZE_MULTIPLIER;
            @SuppressWarnings("unchecked") T[] newItems = (T[]) new Object[newCapacity];
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
            items = newItems;
        } else if (capacity >= USAGE_RATIO_MIN_CAPACITY && usageRatio < USAGE_RATIO_SHRINK_THRESHOLD) {
            // Shrinking
            int newCapacity = capacity / RESIZE_MULTIPLIER;
            @SuppressWarnings("unchecked") T[] newItems = (T[]) new Object[newCapacity];
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
            items = newItems;
        }
    }
}
