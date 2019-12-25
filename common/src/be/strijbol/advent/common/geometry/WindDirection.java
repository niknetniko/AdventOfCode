package be.strijbol.advent.common.geometry;

import be.strijbol.advent.common.tuple.Pair;

/**
 * Represents directions on a grid.
 *
 * @author Niko Strijbol
 */
public enum WindDirection {
    NORTH(1, -1),
    NORTHEAST(1, 0),
    SOUTHEAST(0, 1),
    SOUTH(-1, 1),
    SOUTHWEST(-1, 0),
    NORTHWEST(0, -1);

    private final long x;
    private final long z;

    WindDirection(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public Pair<Long, Long> getOffset() {
        return Pair.of(x, z);
    }

    public static WindDirection fromString(String string) {
        return switch (string.toLowerCase()) {
            case "n" -> NORTH;
            case "ne" -> NORTHEAST;
            case "se" -> SOUTHEAST;
            case "s" -> SOUTH;
            case "sw" -> SOUTHWEST;
            case "nw" -> NORTHWEST;
            default -> throw new IllegalArgumentException("The argument " + string + " is invalid.");
        };
    }

    public Coordinate2D plus(Coordinate2D coordinate) {
        return this.plus(coordinate, 1);
    }

    public Coordinate2D plus(Coordinate2D coordinate, int times) {
        return coordinate.plus(Pair.of(x * times, z * times));
    }
}
