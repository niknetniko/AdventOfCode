package be.strijbol.advent.common.geometry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Niko Strijbol
 */
class Coordinate3DTest {

    @Test
    public void testGetters() {
        final Coordinate3D coordinate = Coordinate3D.of(1, 2, 3);
        assertEquals(1, coordinate.x());
        assertEquals(2, coordinate.y());
        assertEquals(3, coordinate.z());
    }

    @Test
    public void testZero() {
        final Coordinate3D zero = Coordinate3D.ZERO;
        assertEquals(0, zero.x());
        assertEquals(0, zero.y());
        assertEquals(0, zero.z());
    }

    @Test
    public void testAddition() {
        final Coordinate3D adding = Coordinate3D.of(10, 15, 20);
        final Coordinate3D result = Coordinate3D.of(1, 1, 1).plus(adding);
        assertEquals(11, result.x());
        assertEquals(16, result.y());
        assertEquals(21, result.z());
    }
}