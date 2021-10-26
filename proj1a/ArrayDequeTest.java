import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayDequeTest {
    @Test
    public void offsetPosTest() throws NoSuchMethodException, NoSuchFieldException, InvocationTargetException, IllegalAccessException {
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        Class<ArrayDeque<Integer>> cls = (Class<ArrayDeque<Integer>>) deque.getClass();
        Method methodOffsetPos = cls.getDeclaredMethod("offsetPos", int.class, int.class);
        methodOffsetPos.setAccessible(true);
        Field fieldCapacity = cls.getDeclaredField("capacity");
        fieldCapacity.setAccessible(true);

        int capacity = fieldCapacity.getInt(deque);
        assertEquals(capacity - 1, methodOffsetPos.invoke(deque, 0, -1));
        assertEquals(0, methodOffsetPos.invoke(deque, capacity - 1, 1));
        assertEquals(3, methodOffsetPos.invoke(deque, capacity - 1, capacity * 2 + 4));
    }

    @Test
    public void addLastSizeToArrayTest() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        assertEquals(0, deque.size());
        assertArrayEquals(new Integer[]{}, deque.toArray());

        deque.addLast(1);
        assertEquals(1, deque.size());
        assertArrayEquals(new Integer[]{1}, deque.toArray());

        deque.addLast(2);
        assertEquals(2, deque.size());
        assertArrayEquals(new Integer[]{1, 2}, deque.toArray());

        deque.addLast(3);
        assertEquals(3, deque.size());
        assertArrayEquals(new Integer[]{1, 2, 3}, deque.toArray());
    }

    @Test
    public void addFirstTest() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        deque.addFirst(1);
        assertEquals(1, deque.size());
        assertArrayEquals(new Integer[]{1}, deque.toArray());

        deque.addFirst(2);
        assertEquals(2, deque.size());
        assertArrayEquals(new Integer[]{2, 1}, deque.toArray());

        deque.addFirst(3);
        assertEquals(3, deque.size());
        assertArrayEquals(new Integer[]{3, 2, 1}, deque.toArray());
    }

    @Test
    public void printDequeTest() {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            System.setOut(new PrintStream(out));

            ArrayDeque<Integer> ad1 = new ArrayDeque<>();
            ad1.printDeque();
            assertEquals("", out.toString());

            ad1.addLast(1);
            out.reset();
            ad1.printDeque();
            assertEquals("1", out.toString());

            ad1.addLast(2);
            out.reset();
            ad1.printDeque();
            assertEquals("1 2", out.toString());

            ad1.addLast(3);
            out.reset();
            ad1.printDeque();
            assertEquals("1 2 3", out.toString());
        } finally {
            System.setOut(System.out);
        }
    }

    @Test
    public void removeFirst() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        deque.addLast(3);
        deque.addFirst(2);
        deque.addFirst(1);
        assertEquals(1, deque.removeFirst());
        assertEquals(2, deque.size());
        assertEquals(2, deque.removeFirst());
        assertEquals(1, deque.size());
        assertEquals(3, deque.removeFirst());
        assertEquals(0, deque.size());
        assertNull(deque.removeFirst());
        assertEquals(0, deque.size());
    }

    @Test
    public void removeLast() {
        ArrayDeque<Integer> deque = new ArrayDeque<>();

        deque.addFirst(3);
        deque.addLast(2);
        deque.addLast(1);
        assertEquals(1, deque.removeLast());
        assertEquals(2, deque.size());
        assertEquals(2, deque.removeLast());
        assertEquals(1, deque.size());
        assertEquals(3, deque.removeLast());
        assertEquals(0, deque.size());
        assertNull(deque.removeLast());
        assertEquals(0, deque.size());
    }

    @Test
    public void getTest() {
        ArrayDeque<Integer> ad1 = new ArrayDeque<>();
        ad1.addFirst(1);
        ad1.addLast(2);
        ad1.addLast(3);
        // Out of bound tests
        assertNull(ad1.get(-1));
        assertNull(ad1.get(3));
        assertNull(ad1.get(100));
        assertNull(ad1.get(-100));

        assertEquals(1, ad1.get(0));
        assertEquals(3, ad1.get(2));
        assertEquals(2, ad1.get(1));
    }

    @Test
    public void ofTest() {
        ArrayDeque<Integer> ad1 = ArrayDeque.of(1, 2);
        assertEquals(2, ad1.size());
        assertArrayEquals(new Integer[]{1, 2}, ad1.toArray());

        ad1.addLast(3);
        assertArrayEquals(new Integer[]{1, 2, 3}, ad1.toArray());

        ad1.addFirst(3);
        assertArrayEquals(new Integer[]{3, 1, 2, 3}, ad1.toArray());
    }

    @Test
    public void resizeExpandTest() throws NoSuchFieldException, IllegalAccessException {
        ArrayDeque<Integer> ad1 = ArrayDeque.of(1, 2, 3, 4, 5, 6);
        Class<ArrayDeque<Integer>> cls = (Class<ArrayDeque<Integer>>) ad1.getClass();
        Field fieldCapacity = cls.getDeclaredField("capacity");
        fieldCapacity.setAccessible(true);

        assertEquals(8, fieldCapacity.getInt(ad1));
        ad1.addLast(7);
        assertEquals(16, fieldCapacity.getInt(ad1));
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6, 7}, ad1.toArray());

        ad1 = ArrayDeque.of(1, 2, 3, 4, 5, 6);
        ad1.addFirst(0);
        assertEquals(16, fieldCapacity.getInt(ad1));
        assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6}, ad1.toArray());

        ad1 = ArrayDeque.of(1, 2, 3, 4, 5, 6);
        ad1.removeFirst();
        ad1.addLast(7);
        ad1.addLast(8);
        assertEquals(16, fieldCapacity.getInt(ad1));
        assertArrayEquals(new Integer[]{2, 3, 4, 5, 6, 7, 8}, ad1.toArray());
    }

    @Test
    public void resizeShrinkTest() throws NoSuchFieldException, IllegalAccessException {
        ArrayDeque<Integer> ad1 = ArrayDeque.of(0, 0, 1, 2, 3, 4, 0, 0, 0);
        Class<ArrayDeque<Integer>> cls = (Class<ArrayDeque<Integer>>) ad1.getClass();
        Field fieldCapacity = cls.getDeclaredField("capacity");
        fieldCapacity.setAccessible(true);

        ad1.removeFirst();
        ad1.removeFirst();
        ad1.removeLast();
        ad1.removeLast();
        ad1.removeLast();
        assertEquals(16, fieldCapacity.getInt(ad1));
        assertArrayEquals(new Integer[]{1, 2, 3, 4}, ad1.toArray());
        ad1.removeLast();
        assertEquals(8, fieldCapacity.getInt(ad1));
        assertArrayEquals(new Integer[]{1, 2, 3}, ad1.toArray());

        ad1 = ArrayDeque.of(
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 2, 5
        );
        for (int i = 0; i < 10; i++) {
            ad1.removeFirst();
        }
        ad1.addLast(6);
        ad1.addLast(8);
        assertEquals(16, fieldCapacity.getInt(ad1));
        ad1.removeFirst();
        ad1.removeFirst();
        assertArrayEquals(new Integer[]{2, 5, 6, 8}, ad1.toArray());
        ad1.removeFirst();
        assertEquals(8, fieldCapacity.getInt(ad1));
        assertArrayEquals(new Integer[]{5, 6, 8}, ad1.toArray());
    }
}
