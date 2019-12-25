package be.almiro.java.advent.coordinate;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;

import static java.lang.Math.abs;

/**
 * @author Niko Strijbol
 */
public class Distance {

    private final Coordinate pointA;
    private final Coordinate pointB;

    Distance(@NotNull Coordinate pointA, @NotNull Coordinate pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
    }

    public long manhattan() {
        return abs(pointA.x() - pointB.x()) + abs(pointA.y() - pointB.y()) + abs(pointA.z() - pointB.z());
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
}
