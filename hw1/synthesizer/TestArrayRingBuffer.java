package synthesizer;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;


/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void testEnqueue() {
        int capacity = 10;
        ArrayRingBuffer<Integer> rb = new ArrayRingBuffer<>(capacity);
        for (int i = 0; i < capacity; i++) {
            rb.enqueue(i);
            assertEquals(Integer.valueOf(0), rb.peek());
        }
        assertThrows(RuntimeException.class, () -> rb.enqueue(13));
    }

    @Test
    public void testDequeue() {
        int capacity = 10;
        ArrayRingBuffer<Integer> rb = new ArrayRingBuffer<>(capacity);
        assertThrows(RuntimeException.class, rb::dequeue);
        for (int i = 0; i < capacity; i++) {
            rb.enqueue(i);
        }
        for (int i = 0; i < capacity; i++) {
            assertEquals(Integer.valueOf(i), rb.peek());
            assertEquals(Integer.valueOf(i), rb.dequeue());
        }
    }

    @Test
    public void testPeek() {
        ArrayRingBuffer<Integer> rb = new ArrayRingBuffer<>(5);
        assertThrows(NoSuchElementException.class, rb::peek);
    }

    @Test
    public void testIteration() {
        ArrayRingBuffer<Integer> rb = new ArrayRingBuffer<>(5);
        // buffer empty
        for (int e : rb) {
            fail("Should not reach this line");
        }

        for (int i = 0; i < 3; i++) {
            rb.enqueue(i);
        }

        // buffer not full
        int n = 0;
        for (int e : rb) {
            assertEquals(n, e);
            n += 1;
        }

        // buffer full
        rb.enqueue(3);
        rb.enqueue(4);
        n = 0;
        for (int e : rb) {
            assertEquals(n, e);
            n += 1;
        }
    }

    @Test
    public void testIterator() {
        ArrayRingBuffer<Integer> rb = new ArrayRingBuffer<>(5);
        rb.enqueue(0);
        rb.enqueue(1);

        Iterator<Integer> it = rb.iterator();
        while (it.hasNext()) {
            it.next();
        }
        assertThrows(NoSuchElementException.class, it::next);
    }

    /**
     * Calls tests for ArrayRingBuffer.
     */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
