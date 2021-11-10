package synthesizer;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    private class BufferIterator implements Iterator<T> {
        private int currentIndex;

        BufferIterator() {
            currentIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return currentIndex < fillCount;
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            T n = rb[offsetIndex(first, currentIndex)];
            currentIndex += 1;
            return n;
        }
    }

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        this.capacity = capacity;
        first = 0;
        last = 0;
        @SuppressWarnings("unchecked")
        T[] data = (T[]) new Object[capacity];
        rb = data;
        fillCount = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        if (fillCount == capacity) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last = offsetIndex(last, 1);
        fillCount += 1;
    }

    /**
     * Dequeue the oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        if (fillCount == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T dequeued = rb[first];
        first = offsetIndex(first, 1);
        fillCount -= 1;
        return dequeued;
    }

    /**
     * Return the oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        return rb[first];
    }

    private int offsetIndex(int index, int offset) {
        int realIndex = (index + offset) % capacity;
        if (realIndex < 0) {
            realIndex += capacity;
        }
        return realIndex;
    }

    @Override
    public Iterator<T> iterator() {
        return new BufferIterator();
    }
}
