import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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
}
