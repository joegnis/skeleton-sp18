package lab9tester;

import lab9.BSTMap;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests by Brendan Hu, Spring 2015, revised for 2018 by Josh Hug
 */
public class TestBSTMap {

    @Test
    public void sanityGenericsTest() {
        try {
            BSTMap<String, String> a = new BSTMap<String, String>();
            BSTMap<String, Integer> b = new BSTMap<String, Integer>();
            BSTMap<Integer, String> c = new BSTMap<Integer, String>();
            BSTMap<Boolean, Integer> e = new BSTMap<Boolean, Integer>();
        } catch (Exception e) {
            fail();
        }
    }

    //assumes put/size/containsKey/get work
    @Test
    public void sanityClearTest() {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        for (int i = 0; i < 455; i++) {
            b.put("hi" + i, 1 + i);
            //make sure put is working via containsKey and get
            assertNotNull(b.get("hi" + i));
            assertTrue(b.get("hi" + i).equals(1 + i));
            assertTrue(b.containsKey("hi" + i));
        }
        assertEquals(455, b.size());
        b.clear();
        assertEquals(0, b.size());
        for (int i = 0; i < 455; i++) {
            assertTrue(null == b.get("hi" + i) && !b.containsKey("hi" + i));
        }
    }

    // assumes put works
    @Test
    public void sanityContainsKeyTest() {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        assertFalse(b.containsKey("waterYouDoingHere"));
        b.put("waterYouDoingHere", 0);
        assertTrue(b.containsKey("waterYouDoingHere"));
    }

    // assumes put works
    @Test
    public void sanityGetTest() {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        assertEquals(null, b.get("starChild"));
        assertEquals(0, b.size());
        b.put("starChild", 5);
        assertTrue(((Integer) b.get("starChild")).equals(5));
        b.put("KISS", 5);
        assertTrue(((Integer) b.get("KISS")).equals(5));
        assertNotEquals(null, b.get("starChild"));
        assertEquals(2, b.size());
    }

    // assumes put works
    @Test
    public void sanitySizeTest() {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        assertEquals(0, b.size());
        b.put("hi", 1);
        assertEquals(1, b.size());
        for (int i = 0; i < 455; i++) {
            b.put("hi" + i, 1);
        }
        assertEquals(456, b.size());
    }

    //assumes get/containskey work
    @Test
    public void sanityPutTest() {
        BSTMap<String, Integer> b = new BSTMap<String, Integer>();
        b.put("hi", 1);
        assertTrue(b.containsKey("hi"));
        assertTrue(b.get("hi") != null);
    }

    @Test
    public void iteratorTest() {
        BSTMap<Integer, Integer> b = new BSTMap<>();
        for (int i = 0; i < 455; i++) {
            b.put(i, 1 + i);
        }

        Iterator<Integer> iter = b.iterator();
        for (int i = 0; i < 455; i++) {
            assertTrue(iter.hasNext());
            assertEquals(i, iter.next());
        }

        assertFalse(iter.hasNext());
        assertThrows(NoSuchElementException.class, iter::next);
    }

    @Test
    public void keySetTest() {
        BSTMap<Integer, Integer> b = new BSTMap<>();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 455; i++) {
            b.put(i, 1 + i);
            set.add(i);
        }

        assertEquals(set, b.keySet());
    }

    @Test
    public void removeTest() {
        BSTMap<String, Integer> bst = new BSTMap<>();
        bst.put("hi", 1);
        assertEquals(1, bst.remove("hi"));
        assertNull(bst.get("hi"));

        // leaf
        bst.clear();
        bst.put("hi6", 6);
        bst.put("hi4", 4);
        bst.put("hi8", 8);
        assertEquals(4, bst.remove("hi4"));
        assertNull(bst.get("hi4"));

        // two children simple
        bst.clear();
        bst.put("hi6", 6);
        bst.put("hi4", 4);
        bst.put("hi8", 8);
        assertEquals(6, bst.remove("hi6"));
        assertNull(bst.get("hi6"));

        // two children 2
        bst.clear();
        bst.put("hi33", 33);
        bst.put("hi25", 25);
        bst.put("hi40", 40);
        bst.put("hi34", 34);
        assertEquals(33, bst.remove("hi33"));
        assertNull(bst.get("hi33"));

        // only child
        bst.clear();
        bst.put("hi33", 33);
        bst.put("hi25", 25);
        bst.put("hi40", 40);
        bst.put("hi34", 34);
        assertEquals(40, bst.remove("hi40"));
        assertNull(bst.get("hi40"));
    }

}
