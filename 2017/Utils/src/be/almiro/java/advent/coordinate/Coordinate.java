package be.almiro.java.advent.coordinate;

import be.almiro.java.advent.LongTriple;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Niko Strijbol
 */
public class Coordinate {

    public static final Coordinate ZERO = new Coordinate(LongTriple.of(0, 0, 0));

    private final LongTriple longTriple;

    public Coordinate(LongTriple triple) {
        this.longTriple = triple;
    }

    public long x() {
        return longTriple.x();
    }

    public long y() {
        return longTriple.y();
    }

    public long z() {
        return longTriple.z();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return Objects.equals(this.longTriple, that.longTriple);
    }

    @Override
    public int hashCode() {
        return longTriple.hashCode();
    }

    @Override
    public String toString() {
        return longTriple.toString();
    }

    @NotNull
    public Distance distance(@NotNull Coordinate other) {
        return new Distance(this, other);
    }

    public Coordinate plus(LongTriple triple) {
        return new Coordinate(longTriple.plus(triple));
    }
}
