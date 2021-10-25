import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertEquals( capacity - 1, methodOffsetPos.invoke(deque, 0, -1));
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
}
