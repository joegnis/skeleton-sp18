package read81;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.JUnitException;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

public class ArrayListTest {
    private static ArrayList<Integer> intList;
    private static Class<ArrayList<Integer>> intListClass;
    private static Field fieldIntListItems;
    private static Field fieldIntListCapacity;

    @BeforeAll
    public static void setupAll() {
        intList = new ArrayList<>();
        @SuppressWarnings("unchecked") Class<ArrayList<Integer>> cls = (Class<ArrayList<Integer>>) intList.getClass();
        intListClass = cls;

        try {
            fieldIntListItems = intListClass.getDeclaredField("items");
        } catch (NoSuchFieldException e) {
            throw new JUnitException("Field 'items' not found");
        }
        fieldIntListItems.setAccessible(true);

        try {
            fieldIntListCapacity = intListClass.getDeclaredField("capacity");
        } catch (NoSuchFieldException e) {
            throw new JUnitException("Field 'capacity' not found");
        }
        fieldIntListCapacity.setAccessible(true);
    }

    @Test
    public void offsetPosTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ArrayList<Integer> aList = new ArrayList<>();
        Method methodOffsetPos = intListClass.getDeclaredMethod("offsetPos", int.class, int.class);
        methodOffsetPos.setAccessible(true);

        int capacity = fieldIntListCapacity.getInt(aList);
        assertEquals(capacity - 1, methodOffsetPos.invoke(aList, 0, -1));
        assertEquals(0, methodOffsetPos.invoke(aList, capacity - 1, 1));
        assertEquals(3, methodOffsetPos.invoke(aList, capacity - 1, capacity * 2 + 4));
    }

    @Test
    public void addLastSizeToArrayTest() {
        ArrayList<Integer> aList = new ArrayList<>();

        assertEquals(0, aList.size());
        assertArrayEquals(new Integer[]{}, aList.toArray());

        aList.add(1);
        assertEquals(1, aList.size());
        assertArrayEquals(new Integer[]{1}, aList.toArray());

        aList.add(2);
        assertEquals(2, aList.size());
        assertArrayEquals(new Integer[]{1, 2}, aList.toArray());

        aList.add(3);
        assertEquals(3, aList.size());
        assertArrayEquals(new Integer[]{1, 2, 3}, aList.toArray());
    }

    @Test
    public void addFirstTest() {
        ArrayList<Integer> aList = new ArrayList<>();

        aList.add(1);
        assertEquals(1, aList.size());
        assertArrayEquals(new Integer[]{1}, aList.toArray());

        aList.add(0, 2);
        assertEquals(2, aList.size());
        assertArrayEquals(new Integer[]{2, 1}, aList.toArray());

        aList.add(0, 3);
        assertEquals(3, aList.size());
        assertArrayEquals(new Integer[]{3, 2, 1}, aList.toArray());
    }

    @Test
    public void removeFirstTest() {
        ArrayList<Integer> aList = new ArrayList<>();

        aList.add(3);
        aList.add(0, 2);
        aList.add(0, 1);
        assertEquals(1, aList.remove(0));
        assertEquals(2, aList.size());
        assertEquals(2, aList.remove(0));
        assertEquals(1, aList.size());
        assertEquals(3, aList.remove(0));
        assertEquals(0, aList.size());
        assertThrows(IndexOutOfBoundsException.class, () -> aList.remove(0));
        assertEquals(0, aList.size());
    }

    @Test
    public void removeLastTest() {
        ArrayList<Integer> aList = new ArrayList<>();

        aList.add(0, 3);
        aList.add(2);
        aList.add(1);
        assertEquals(1, aList.remove(aList.size() - 1));
        assertEquals(2, aList.size());
        assertEquals(2, aList.remove(aList.size() - 1));
        assertEquals(1, aList.size());
        assertEquals(3, aList.remove(aList.size() - 1));
        assertEquals(0, aList.size());
        assertThrows(IndexOutOfBoundsException.class, () -> aList.remove(aList.size() - 1));
        assertEquals(0, aList.size());
    }

    @Test
    public void removeAfterResizeTest() throws IllegalAccessException {
        ArrayList<Integer> aList = new ArrayList<>();
        assertEquals(8, fieldIntListCapacity.getInt(aList));
        for (int i = 7; i >= 0; i--) {
            aList.add(0, i);
        }
        assertEquals(16, fieldIntListCapacity.getInt(aList));
        for (int i = 0; i < 8; i++) {
            assertEquals(i, aList.remove(0));
        }

        aList = new ArrayList<>();
        assertEquals(8, fieldIntListCapacity.getInt(aList));
        for (int i = 0; i < 8; i++) {
            aList.add(0, i);
        }
        assertEquals(16, fieldIntListCapacity.getInt(aList));
        for (int i = 0; i < 8; i++) {
            assertEquals(i, aList.remove(aList.size() - 1));
        }
    }

    @Test
    public void getTest() {
        ArrayList<Integer> ad1 = new ArrayList<>();
        ad1.add(0, 1);
        ad1.add(2);
        ad1.add(3);
        // Out of bound tests
        assertThrows(IndexOutOfBoundsException.class, () -> ad1.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> ad1.get(3));
        assertThrows(IndexOutOfBoundsException.class, () -> ad1.get(100));
        assertThrows(IndexOutOfBoundsException.class, () -> ad1.get(-100));

        assertEquals(1, ad1.get(0));
        assertEquals(3, ad1.get(2));
        assertEquals(2, ad1.get(1));
    }

    @Test
    public void ofTest() {
        ArrayList<Integer> aList = ArrayList.of(1, 2);
        assertEquals(2, aList.size());
        assertArrayEquals(new Integer[]{1, 2}, aList.toArray());

        aList.add(3);
        assertArrayEquals(new Integer[]{1, 2, 3}, aList.toArray());

        aList.add(0, 3);
        assertArrayEquals(new Integer[]{3, 1, 2, 3}, aList.toArray());
    }

    @Test
    public void resizeExpandTest() throws IllegalAccessException {
        ArrayList<Integer> aList = ArrayList.of(1, 2, 3, 4, 5, 6);
        assertEquals(8, fieldIntListCapacity.getInt(aList));
        aList.add(7);
        assertEquals(16, fieldIntListCapacity.getInt(aList));
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, 6, 7}, aList.toArray());

        aList = ArrayList.of(1, 2, 3, 4, 5, 6);
        aList.add(0, 0);
        assertEquals(16, fieldIntListCapacity.getInt(aList));
        assertArrayEquals(new Integer[]{0, 1, 2, 3, 4, 5, 6}, aList.toArray());

        aList = ArrayList.of(1, 2, 3, 4, 5, 6);
        aList.remove(0);
        aList.add(7);
        aList.add(8);
        assertEquals(16, fieldIntListCapacity.getInt(aList));
        assertArrayEquals(new Integer[]{2, 3, 4, 5, 6, 7, 8}, aList.toArray());
    }

    @Test
    public void resizeShrinkTest() throws IllegalAccessException {
        ArrayList<Integer> aList = ArrayList.of(0, 0, 1, 2, 3, 4, 0, 0, 0);
        aList.remove(0);
        aList.remove(0);
        aList.remove(aList.size() - 1);
        aList.remove(aList.size() - 1);
        aList.remove(aList.size() - 1);
        assertEquals(16, fieldIntListCapacity.getInt(aList));
        assertArrayEquals(new Integer[]{1, 2, 3, 4}, aList.toArray());
        aList.remove(aList.size() - 1);
        assertEquals(8, fieldIntListCapacity.getInt(aList));
        assertArrayEquals(new Integer[]{1, 2, 3}, aList.toArray());

        aList = ArrayList.of(
                0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 2, 5
        );
        for (int i = 0; i < 10; i++) {
            aList.remove(0);
        }
        aList.add(6);
        aList.add(8);
        assertEquals(16, fieldIntListCapacity.getInt(aList));
        aList.remove(0);
        aList.remove(0);
        assertArrayEquals(new Integer[]{2, 5, 6, 8}, aList.toArray());
        aList.remove(0);
        assertEquals(8, fieldIntListCapacity.getInt(aList));
        assertArrayEquals(new Integer[]{5, 6, 8}, aList.toArray());
    }

    @Test
    public void isEmptyTest() {
        ArrayList<Integer> aList = new ArrayList<>();
        assertTrue(aList.isEmpty());
        aList.add(0, 1);
        assertFalse(aList.isEmpty());
        aList.remove(aList.size() - 1);
        assertTrue(aList.isEmpty());
    }

    @Test
    public void setTest() {
        ArrayList<Integer> aList = ArrayList.of(1, 2, 3, 4, 5);
        aList.set(0, -1);
        assertArrayEquals(new Integer[]{-1, 2, 3, 4, 5}, aList.toArray());
        aList.set(1, -2);
        assertArrayEquals(new Integer[]{-1, -2, 3, 4, 5}, aList.toArray());
        aList.set(3, -4);
        assertArrayEquals(new Integer[]{-1, -2, 3, -4, 5}, aList.toArray());
    }

    @Test
    public void testAddToArrayCircleAroundEnd() throws IllegalAccessException {
        // Build the array
        ArrayList<Integer> aList = ArrayList.of(1, 2, 3, 4, 5, 6);
        aList.remove(0);
        aList.remove(0);
        aList.add(1);
        aList.add(2);
        aList.remove(0);
        aList.remove(0);
        aList.add(3);
        aList.add(4);
        aList.remove(0);
        aList.remove(0);
        assertArrayEquals(new Integer[]{1, 2, 3, 4}, aList.toArray());
        assertArrayEquals(new Integer[]{3, 4, 3, 4, 5, 6, 1, 2}, unpackAsArray(fieldIntListItems.get(aList)));

        // When inserting to the first half of the array
        aList.add(0, 8);
        assertArrayEquals(new Integer[]{8, 1, 2, 3, 4}, aList.toArray());
        assertArrayEquals(new Integer[]{3, 4, 3, 4, 5, 8, 1, 2}, unpackAsArray(fieldIntListItems.get(aList)));

        aList.remove(0);
        aList.add(1, 10);
        assertArrayEquals(new Integer[]{1, 10, 2, 3, 4}, aList.toArray());
        assertArrayEquals(new Integer[]{3, 4, 3, 4, 5, 1, 10, 2}, unpackAsArray(fieldIntListItems.get(aList)));

        aList.remove(0);
        aList.remove(0);
        aList.add(1, 11);
        assertArrayEquals(new Integer[]{2, 11, 3, 4}, aList.toArray());
        assertArrayEquals(new Integer[]{3, 4, 3, 4, 5, 1, 2, 11}, unpackAsArray(fieldIntListItems.get(aList)));

        // When lenBefore == lenAfter
        aList.add(2, 5);
        assertArrayEquals(new Integer[]{2, 11, 5, 3, 4}, aList.toArray());
        assertArrayEquals(new Integer[]{3, 4, 3, 4, 5, 2, 11, 5}, unpackAsArray(fieldIntListItems.get(aList)));

        // When inserting to the last half of the array
        aList.add(4, 9);
        assertArrayEquals(new Integer[]{2, 11, 5, 3, 9, 4}, aList.toArray());
        assertArrayEquals(new Integer[]{3, 9, 4, 4, 5, 2, 11, 5}, unpackAsArray(fieldIntListItems.get(aList)));

        aList.remove(0);
        aList.add(5, 44);  // adds to rear
        assertArrayEquals(new Integer[]{11, 5, 3, 9, 4, 44}, aList.toArray());
        assertArrayEquals(new Integer[]{3, 9, 4, 44, 5, 2, 11, 5}, unpackAsArray(fieldIntListItems.get(aList)));

        // Shifts two parts to the right after inserting
        aList.remove(aList.size() - 1);
        aList.remove(aList.size() - 1);
        aList.remove(aList.size() - 1);
        aList.add(0, 3);
        aList.add(0, 4);
        aList.add(0, 5);
        aList.add(0, 6);
        assertArrayEquals(new Integer[]{6, 5, 4, 3, 11, 5, 3}, aList.toArray());
        aList.add(5, 4);
        assertArrayEquals(new Integer[]{6, 5, 4, 3, 11, 4, 5, 3}, aList.toArray());
        aList.add(0, 1);
        aList.add(0, 1);
        aList.add(0, 1);
        aList.add(6, -5);
        assertArrayEquals(new Integer[]{1, 1, 1, 6, 5, 4, -5, 3, 11, 4, 5, 3}, aList.toArray());
    }

    @Test
    public void testAddToArrayInMiddle() throws IllegalAccessException {
        ArrayList<Integer> aList = ArrayList.of(1, 2, 3, 4, 5);
        aList.remove(0);
        aList.remove(0);
        assertArrayEquals(new Integer[]{3, 4, 5}, aList.toArray());
        assertArrayEquals(new Integer[]{1, 2, 3, 4, 5, null, null, null}, unpackAsArray(fieldIntListItems.get(aList)));

        aList.add(0, 1);
        assertArrayEquals(new Integer[]{1, 3, 4, 5}, aList.toArray());
        assertArrayEquals(new Integer[]{1, 1, 3, 4, 5, null, null, null}, unpackAsArray(fieldIntListItems.get(aList)));

        aList.add(1, 4);
        assertArrayEquals(new Integer[]{1, 4, 3, 4, 5}, aList.toArray());
        assertArrayEquals(new Integer[]{1, 4, 3, 4, 5, null, null, null}, unpackAsArray(fieldIntListItems.get(aList)));

        aList.add(4, -4);
        assertArrayEquals(new Integer[]{1, 4, 3, 4, -4, 5}, aList.toArray());
        assertArrayEquals(new Integer[]{1, 4, 3, 4, -4, 5, null, null}, unpackAsArray(fieldIntListItems.get(aList)));

        aList.add(6, -5);
        assertArrayEquals(new Integer[]{1, 4, 3, 4, -4, 5, -5}, aList.toArray());
    }

    @Test
    public void testAddToArrayEdgeCases() throws IllegalAccessException {
        ArrayList<Integer> aList = ArrayList.of(1, 2, 3, 4);
        // realIndex == 0
        aList.add(0, -1);
        assertArrayEquals(new Integer[]{-1, 1, 2, 3, 4}, aList.toArray());
        assertArrayEquals(new Integer[]{1, 2, 3, 4, null, null, null, -1}, unpackAsArray(fieldIntListItems.get(aList)));

        // Shifts two parts to the left after inserting
        aList.add(2, -3);
        assertArrayEquals(new Integer[]{-1, 1, -3, 2, 3, 4}, aList.toArray());
        assertArrayEquals(new Integer[]{-3, 2, 3, 4, null, null, -1, 1}, unpackAsArray(fieldIntListItems.get(aList)));
        aList.add(3, -4);
        assertArrayEquals(new Integer[]{-1, 1, -3, -4, 2, 3, 4}, aList.toArray());

        // Index out of bound
        assertThrows(IndexOutOfBoundsException.class, () -> aList.add(-1, 100));
        assertThrows(IndexOutOfBoundsException.class, () -> aList.add(-5, 100));
        assertThrows(IndexOutOfBoundsException.class, () -> aList.add(aList.size() + 1, 100));
        assertThrows(IndexOutOfBoundsException.class, () -> aList.add(aList.size() + 10, 100));
    }

    private static Object[] unpackAsArray(Object arrObj) {
        Object[] array = new Object[Array.getLength(arrObj)];
        for (int i = 0; i < array.length; i++) {
            array[i] = Array.get(arrObj, i);
        }
        return array;
    }
}
