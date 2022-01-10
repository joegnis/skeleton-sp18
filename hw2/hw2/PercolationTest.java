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
    public void testFirstRowNotFullInitially() {
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
    public void testOtherBottomSitesNotFullAfterPercolates() {
        Percolation p = new Percolation(3);
        p.open(0, 0);
        p.open(1, 0);
        p.open(2, 0);
        p.open(2, 2);
        assertTrue(p.isFull(2, 0));
        assertFalse(p.isFull(2, 2));
    }

    @Test
    public void testRepeatedlyOpen() {
        Percolation p = new Percolation(3);
        p.open(0, 1);
        p.open(0, 1);
        p.open(0, 1);
        assertEquals(1, p.numberOfOpenSites());
    }

    @Test
    public void testSmallN() {
        Percolation p = new Percolation(1);
        assertFalse(p.percolates());
        p.open(0, 0);
        assertTrue(p.percolates());

        Percolation p2 = new Percolation(2);
        assertFalse(p2.percolates());
        p2.open(0, 0);
        p2.open(1, 0);
        assertTrue(p2.percolates());
    }

    @Test
    public void testOutOfBound() {
        Percolation p = new Percolation(3);

        assertThrows(IndexOutOfBoundsException.class, () -> p.open(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> p.isOpen(1, 3));
        assertThrows(IndexOutOfBoundsException.class, () -> p.isFull(3, 1));
    }

    @Test
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new Percolation(-10));
    }
}