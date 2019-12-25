package be.strijbol.advent.common.geometry;

import be.strijbol.advent.common.tuple.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * Convenient wrapper for coordinates in two dimensions.
 *
 * @author Niko Strijbol
 */
public class Coordinate2D {

    public static final Coordinate2D ZERO = new Coordinate2D(0L, 0L);

    private final Coordinate inner;

    private Coordinate2D(long x, long y) {
        inner = Coordinate.of(x, y);
    }

    public static Coordinate2D of(long x, long y) {
        return new Coordinate2D(x, y);
    }

    public static Coordinate2D of(Pair<Long, Long> pair) {
        return Coordinate2D.of(pair.getLeft(), pair.getRight());
    }

    public long x() {
        return inner.getPoint(1);
    }

    public long y() {
        return inner.getPoint(2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate2D that = (Coordinate2D) o;
        return inner.equals(that.inner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(inner);
    }

    @Override
    public String toString() {
        return inner.toString();
    }

    @NotNull
    public Distance distance(@NotNull Coordinate2D other) {
        return new Distance(this.inner, other.inner);
    }

    @NotNull
    public Coordinate2D plus(@NotNull Coordinate2D coordinate) {
        return Coordinate2D.of(
                this.x() + coordinate.x(),
                this.y() + coordinate.y()
        );
    }

    @NotNull
    public Coordinate2D plus(@NotNull Pair<Long, Long> pair) {
        return Coordinate2D.of(
                this.x() + pair.getLeft(),
                this.y() + pair.getRight()
        );
    }
}
