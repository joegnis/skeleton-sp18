import java.util.Arrays;

/** Array based list.
 *  @author Josh Hug
 */

public class AList {
    private int[] array;
    private int capacity;
    private int size;

    private final static int INIT_CAPACITY = 50;

    /** Creates an empty list. */
    public AList() {
        size = 0;
        capacity = INIT_CAPACITY;
        array = new int[capacity];
    }

    /** Inserts X into the back of the list. */
    public void addLast(int x) {
        array[size] = x;
        size += 1;

        if (size == capacity) {
            resizeTo(capacity * 2);
        }
    }

    /** Returns the item from the back of the list. */
    public int getLast() {
        return array[size - 1];
    }
    /** Gets the ith item in the list (0 is the front). */
    public int get(int i) {
        return array[i];
    }

    /** Returns the number of items in the list. */
    public int size() {
        return size;
    }

    /** Deletes item from back of the list and
     * returns deleted item. */
    public int removeLast() {
        int removed = array[size - 1];
        size -= 1;
        if (size <= capacity / 2 && size > INIT_CAPACITY) {
            resizeTo(capacity / 2);
        }

        return removed;
    }

    private void resizeTo(int newCapacity) {
        int[] newArray = new int[newCapacity];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
        capacity = newCapacity;
    }
}