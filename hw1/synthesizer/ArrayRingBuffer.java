package synthesizer;

public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;
    private int size;

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
        size = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        if (size == capacity) {
            throw new RuntimeException("Ring buffer overflow");
        }
        rb[last] = x;
        last = offsetIndex(last, 1);
        size += 1;
    }

    /**
     * Dequeue the oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        if (size == 0) {
            throw new RuntimeException("Ring buffer underflow");
        }
        T dequeued = rb[first];
        first = offsetIndex(first, 1);
        size -= 1;
        return dequeued;
    }

    /**
     * Return the oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        if (size == 0) {
            return null;
        }
        return rb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.

    private int offsetIndex(int index, int offset) {
        int realIndex = (index + offset) % capacity;
        if (realIndex < 0) {
            realIndex += capacity;
        }
        return realIndex;
    }
}
