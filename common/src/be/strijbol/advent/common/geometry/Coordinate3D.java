package be.strijbol.advent.common.geometry;

import be.strijbol.advent.common.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Niko Strijbol
 */
public class Coordinate3D {

    public static final Coordinate3D ZERO = new Coordinate3D(0L, 0L, 0L);

    private final Coordinate inner;

    private Coordinate3D(long x, long y, long z) {
        this.inner = Coordinate.of(x, y, z);
    }

    public static Coordinate3D of(long x, long y, long z) {
        return new Coordinate3D(x, y, z);
    }

    public static Coordinate3D of(Triple<Long, Long, Long> triple) {
        return Coordinate3D.of(triple.getLeft(), triple.getMiddle(), triple.getRight());
    }

    public long x() {
        return inner.getPoint(1);
    }

    public long y() {
        return inner.getPoint(2);
    }

    public long z() {
        return inner.getPoint(3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate3D that = (Coordinate3D) o;
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
    public Distance distance(@NotNull Coordinate3D other) {
        return new Distance(this.inner, other.inner);
    }

    @NotNull
    public Coordinate3D plus(@NotNull Coordinate3D other) {
        return new Coordinate3D(
                this.x() + other.x(),
                this.y() + other.y(),
                this.z() + other.z()
        );
    }

    @NotNull
    public Coordinate3D plus(@NotNull Triple<Long, Long, Long> other) {
        return Coordinate3D.of(other).plus(this);
    }
}
