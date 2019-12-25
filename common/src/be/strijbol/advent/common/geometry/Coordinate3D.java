package be.strijbol.advent.common.geometry;

import be.strijbol.advent.common.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Niko Strijbol
 */
public class Coordinate3D {

    public static final Coordinate3D ZERO = new Coordinate3D(Triple.of(0L, 0L, 0L));

    private final Triple<Long, Long, Long> longTriple;

    private Coordinate3D(Triple<Long, Long, Long> triple) {
        this.longTriple = triple;
    }

    public static Coordinate3D of(long x, long y, long z) {
        return new Coordinate3D(Triple.of(x, y, z));
    }

    public static Coordinate3D of(Triple<Long, Long, Long> triple) {
        return new Coordinate3D(triple);
    }

    public long x() {
        return longTriple.getLeft();
    }

    public long y() {
        return longTriple.getMiddle();
    }

    public long z() {
        return longTriple.getRight();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate3D that = (Coordinate3D) o;
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
    public Distance distance(@NotNull Coordinate3D other) {
        return new Distance(this, other);
    }

    @NotNull
    public Coordinate3D plus(@NotNull Coordinate3D triple) {
        return new Coordinate3D(Triple.plus(this.longTriple, triple.longTriple));
    }

    @NotNull
    public Coordinate3D plus(@NotNull Triple<Long, Long, Long> triple) {
        return new Coordinate3D(Triple.plus(this.longTriple, triple));
    }
}
