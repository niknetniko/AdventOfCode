package be.strijbol.advent.common.geometry;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;

/**
 * @author Niko Strijbol
 */
public class Distance {

    private final Coordinate pointA;
    private final Coordinate pointB;

    Distance(@NotNull Coordinate pointA, @NotNull Coordinate pointB) {
        if (pointA.getDimension() != pointB.getDimension()) {
            throw new IllegalArgumentException("Distance between two points requires the same dimension.");
        }
        this.pointA = pointA;
        this.pointB = pointB;
    }

    public long manhattan() {
        assert pointA.getDimension() == pointB.getDimension();
        long distance = 0;
        for (int i = 1; i <= pointA.getDimension(); i++) {
            distance += Math.abs(pointA.getPoint(i) - pointB.getPoint(i));
        }
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Distance distance = (Distance) o;
        return Objects.equals(pointA, distance.pointA) &&
                Objects.equals(pointB, distance.pointB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pointA, pointB);
    }

    @Override
    public String toString() {
        return "Distance(from=" + pointA.toString() + ", to=" + pointB.toString() + ")";
    }

    public static Comparator<Distance> manhattanComparator() {
        return Comparator.comparing(Distance::manhattan);
    }

    public Coordinate getPointA() {
        return pointA;
    }

    public Coordinate getPointB() {
        return pointB;
    }
}
