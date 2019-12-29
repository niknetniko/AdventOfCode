package be.strijbol.advent.common.geometry;

/**
 * @author Niko Strijbol
 */
public class Coordinates {

    public static int toOneDimension(int x, int y, int width) {
        return x + width * y;
    }

    public static Coordinate2D toTwoDimensions(int i, int width) {
        return Coordinate2D.of(i % width, Math.floorDiv(i, width));
    }
}
