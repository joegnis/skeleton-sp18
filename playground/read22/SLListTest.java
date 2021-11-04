package read22;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SLListTest {

    @Test
    public void testAddFirst() {
        SLList list = new SLList();
        list.addFirst(1);
        assertEquals(1, list.getFirst());

        list.addFirst(2);
        assertEquals(2, list.getFirst());
    }

    @Test
    public void testSize() {
        SLList list = new SLList();
        assertEquals(0, list.size());

        list = new SLList(1);
        assertEquals(1, list.size());

        list.addFirst(2);
        assertEquals(2, list.size());

        list.addFirst(3);
        assertEquals(3, list.size());
    }

    @Test
    public void testAddLast() {
        SLList list = new SLList();
        list.addLast(1);
        assertEquals(1, list.getFirst());
    }
}