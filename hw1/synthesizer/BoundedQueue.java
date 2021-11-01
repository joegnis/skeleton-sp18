package synthesizer;

public interface BoundedQueue<T> {
    /**
     * Returns size of the buffer
     */
    int capacity();

    /**
     * Returns number of items currently in the buffer
     */
    int fillCount();

    /**
     * Adds item x to the end
     */
    void enqueue(T x);

    /**
     * Deletes and returns item from the front
     */
    T dequeue();

    /**
     * Returns item from the front
     */
    T peek();

    /**
     * Returns true if the buffer is empty
     */
    default boolean isEmpty() {
        return fillCount() == 0;
    }

    /**
     * Returns true if the buffer is full
     */
    default boolean isFull() {
        return fillCount() == capacity();
    }
}
