package be.strijbol.advent.common.geometry;

import java.util.Arrays;
import java.util.Objects;

/**
 * A coordinate in a dimension n, with long coordinates.
 *
 * @author Niko Strijbol
 */
public class Coordinate {

    private final int dimension;
    private final long[] points;

    private Coordinate(long[] points) {
        if (points.length < 2) {
            throw new IllegalArgumentException("Dimension must be at least 2.");
        }
        this.dimension = points.length;
        this.points = points;
    }

    public static Coordinate of(long... points) {
        return new Coordinate(points);
    }

    public long getPoint(int dimension) {
        if (dimension <= 0 || dimension > this.dimension) {
            throw new IndexOutOfBoundsException("Dimension must be between [1," + this.dimension + "], got " + dimension);
        }
        return points[dimension - 1];
    }

    public Coordinate move(long[] deltas) {
        if (deltas.length < 1 || deltas.length > this.dimension) {
            throw new IllegalArgumentException("Must provide [1, " + this.dimension + "] deltas.");
        }
        var copy = Arrays.copyOf(this.points, this.dimension);
        for (int i = 0; i < deltas.length; i++) {
            copy[i] = copy[i] + deltas[i];
        }
        return new Coordinate(copy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return dimension == that.dimension &&
                Arrays.equals(points, that.points);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(dimension);
        result = 31 * result + Arrays.hashCode(points);
        return result;
    }

    @Override
    public String toString() {
        return "Coordinate" + Arrays.toString(points);
    }

    public int getDimension() {
        return dimension;
    }
}
