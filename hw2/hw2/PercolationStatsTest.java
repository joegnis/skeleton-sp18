package hw2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PercolationStatsTest {
    @Test
    public void testConstructor() {
        PercolationFactory pf = new PercolationFactory();
        assertThrows(IllegalArgumentException.class, () -> new PercolationStats(-10, 30, pf));
        assertThrows(IllegalArgumentException.class, () -> new PercolationStats(10, -30, pf));
    }
}