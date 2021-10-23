/**
 * Double ended deque implemented by array
 *
 * Time complexity:
 * - {@link #addFirst(T)}, {@link #addLast(T)}, {@link #removeLast()}, {@link #removeLast()} take constant time,
 *   except during resizing operations
 * - get, size take constant time
 * Other details
 * - The starting size of array is 8.
 * - The amount of memory used at any given time is proportional to the number of items.
 * - For arrays of length 16 or more, usage factor is at least 25%. For smaller arrays, usage factor is arbitrarily low.
 * - The array is circular.
 */
public class ArrayDeque<T> implements Deque<T> {

    public ArrayDeque() {

    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
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
}
