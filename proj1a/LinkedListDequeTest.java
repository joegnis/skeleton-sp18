import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/** Performs some basic linked list tests. */
public class LinkedListDequeTest {
	
	/** Adds a few things to the list, checking isEmpty() and size() are correct,
	  * finally printing the results. 
	  *
	  * && is the "and" operation. */
	@Test
	public void addIsEmptySizeTest() {
		LinkedListDeque<String> lld1 = new LinkedListDeque<>();

		assertTrue(lld1.isEmpty());

		lld1.addFirst("front");
		
		assertEquals(1, lld1.size());
		assertFalse(lld1.isEmpty());

		lld1.addLast("middle");
		assertEquals(2, lld1.size());

		lld1.addLast("back");
		assertEquals(3, lld1.size());

		// System.out.println("Printing out deque: ");
		// lld1.printDeque();
	}

	/** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
	@Test
	public void addRemoveTest() {
		LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
		// should be empty 
		assertTrue(lld1.isEmpty());

		lld1.addFirst(10);
		// should not be empty 
		assertFalse(lld1.isEmpty());

		lld1.removeFirst();
		// should be empty 
		assertTrue(lld1.isEmpty());
	}

	@Test
	public void addFirstToArraySizeTest() {
		LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
		assertArrayEquals(new Integer[]{}, lld1.toArray());
		assertEquals(0, lld1.size());

		lld1.addFirst(1);
		assertArrayEquals(new Integer[]{1}, lld1.toArray());
		assertEquals(1, lld1.size());

		lld1.addFirst(2);
		assertArrayEquals(new Integer[]{2, 1}, lld1.toArray());
		assertEquals(2, lld1.size());

		lld1.addFirst(3);
		assertArrayEquals(new Integer[]{3, 2, 1}, lld1.toArray());
		assertEquals(3, lld1.size());
	}

	@Test
	public void removeFirstTest() {
		LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
		lld1.addFirst(1);
		lld1.addFirst(2);
		lld1.addFirst(3);

		Integer removed = lld1.removeFirst();
		assertEquals(3, removed);
		assertArrayEquals(new Integer[]{2, 1}, lld1.toArray());
		assertEquals(2, lld1.size());

		removed = lld1.removeFirst();
		assertEquals(2, removed);
		assertArrayEquals(new Integer[]{1}, lld1.toArray());
		assertEquals(1, lld1.size());

		removed = lld1.removeFirst();
		assertEquals(1, removed);
		assertArrayEquals(new Integer[]{}, lld1.toArray());
		assertEquals(0, lld1.size());

		assertNull(lld1.removeFirst());
		assertEquals(0, lld1.size());
		assertArrayEquals(new Integer[]{}, lld1.toArray());
	}

	@Test
	public void addLastTest() {
		LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
		lld1.addLast(1);
		assertArrayEquals(new Integer[]{1}, lld1.toArray());
		assertEquals(1, lld1.size());

		lld1.addLast(2);
		assertArrayEquals(new Integer[]{1, 2}, lld1.toArray());
		assertEquals(2, lld1.size());

		lld1.addLast(3);
		assertArrayEquals(new Integer[]{1, 2, 3}, lld1.toArray());
		assertEquals(3, lld1.size());
	}

	@Test
	public void removeLastTest() {
		LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
		lld1.addLast(1);
		lld1.addLast(2);
		lld1.addLast(3);

		Integer removed = lld1.removeLast();
		assertEquals(3, removed);
		assertArrayEquals(new Integer[]{1, 2}, lld1.toArray());
		assertEquals(2, lld1.size());

		removed = lld1.removeLast();
		assertEquals(2, removed);
		assertArrayEquals(new Integer[]{1}, lld1.toArray());
		assertEquals(1, lld1.size());

		removed = lld1.removeFirst();
		assertEquals(1, removed);
		assertArrayEquals(new Integer[]{}, lld1.toArray());
		assertEquals(0, lld1.size());

		assertNull(lld1.removeLast());
		assertEquals(0, lld1.size());
		assertArrayEquals(new Integer[]{}, lld1.toArray());
	}

	@Test
	public void getTest() {
		LinkedListDeque<Integer> lld1 = new LinkedListDeque<>();
		lld1.addLast(1);
		lld1.addLast(2);
		lld1.addLast(3);

		assertNull(lld1.get(-1));
		assertNull(lld1.get(3));
		assertNull(lld1.get(100));
		assertEquals(1, lld1.get(0));
		assertEquals(2, lld1.get(1));
		assertEquals(3, lld1.get(2));
	}
}