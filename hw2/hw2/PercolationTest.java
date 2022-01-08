package hw2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PercolationTest {

    @Test
    public void testSimplePercolates() {
        Percolation p = new Percolation(3);

        assertEquals(0, p.numberOfOpenSites());
        assertFalse(p.isOpen(0, 0));
        assertFalse(p.isFull(2, 2));
        assertFalse(p.percolates());

        p.open(0, 1);
        p.open(1, 1);
        assertTrue(p.isFull(1, 1));
        assertFalse(p.isFull(2, 1));
        p.open(2, 1);

        assertTrue(p.isOpen(0, 1));
        assertTrue(p.isOpen(1, 1));
        assertTrue(p.isOpen(2, 1));
        assertFalse(p.isOpen(0, 0));
        assertTrue(p.isFull(1, 1));
        assertTrue(p.percolates());
    }

    @Test
    public void testPercolates2() {
        Percolation p = new Percolation(4);
        p.open(0, 0);
        p.open(0, 1);
        p.open(1, 1);
        p.open(2, 1);
        p.open(2, 2);
        p.open(3, 2);
        assertTrue(p.percolates());
    }

    @Test
    public void testUnion() {
        Percolation p = new Percolation(4);

        p.open(0, 1);
        p.open(3, 1);
        p.open(2, 1);
        assertFalse(p.isFull(2, 1));
        assertFalse(p.isFull(3, 1));

        p.open(1, 1);
        assertTrue(p.isFull(2, 1));
        assertTrue(p.isFull(3, 1));
        assertTrue(p.percolates());
    }

    @Test
    public void testFull() {
        Percolation p = new Percolation(3);
        assertFalse(p.isFull(0, 0));
        assertFalse(p.isFull(0, 1));
        assertFalse(p.isFull(0, 2));

        p.open(0, 1);
        assertFalse(p.isFull(0, 0));
        assertTrue(p.isFull(0, 1));
        assertFalse(p.isFull(0, 2));
    }

    @Test
    public void testOutOfBound() {
        Percolation p = new Percolation(3);

        assertThrows(IndexOutOfBoundsException.class, () -> p.open(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> p.isOpen(1, 3));
        assertThrows(IndexOutOfBoundsException.class, () -> p.isFull(3, 1));
    }

}