package be.almiro.java.advent;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * @author Niko Strijbol
 */
public class LongTriple {

    private final long x;
    private final long y;
    private final long z;

    private LongTriple(long x, long y, long z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static LongTriple of(long x, long y, long z) {
        return new LongTriple(x, y, z);
    }

    public long x() {
        return x;
    }

    public long y() {
        return y;
    }

    public long z() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LongTriple that = (LongTriple) o;
        return x == that.x &&
                y == that.y &&
                z == that.z;
    }

    /**
     * Must be in the correct format, e.g. <X,Y,Z>.
     */
    public static LongTriple parse(String string) {
        string = string.substring(1, string.length() - 1);
        String[] parts = string.split(",");
        assert parts.length == 3;
        return LongTriple.of(
                Long.parseLong(parts[0]),
                Long.parseLong(parts[1]),
                Long.parseLong(parts[2])
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "<" + x + "," + y + "," + z + '>';
    }

    @NotNull
    public LongTriple plus(@NotNull LongTriple other) {
        return new LongTriple(this.x + other.x, this.y + other.y, this.z + other.z);
    }
}
