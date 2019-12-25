package be.strijbol.advent.common.geometry;

import be.strijbol.advent.common.tuple.Pair;

/**
 * Represents directions on a grid.
 *
 * @author Niko Strijbol
 */
public enum GridDirection {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private final long x;
    private final long y;

    GridDirection(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static GridDirection fromString(String string) {
        return switch (string.toLowerCase()) {
            case "u" -> UP;
            case "d" -> DOWN;
            case "l" -> LEFT;
            case "r" -> RIGHT;
            default -> throw new IllegalArgumentException("The argument " + string + " is invalid.");
        };
    }

    public Coordinate2D plus(Coordinate2D coordinate) {
        return coordinate.plus(Pair.of(x, y));
    }
}
