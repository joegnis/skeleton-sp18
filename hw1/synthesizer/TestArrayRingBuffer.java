package synthesizer;

import org.junit.Test;

import static org.junit.Assert.*;


/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void testEnqueue() {
        int capacity = 10;
        ArrayRingBuffer<Integer> rb = new ArrayRingBuffer<>(capacity);
        assertNull(rb.peek());
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
        assertNull(rb.peek());
    }

    /**
     * Calls tests for ArrayRingBuffer.
     */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
