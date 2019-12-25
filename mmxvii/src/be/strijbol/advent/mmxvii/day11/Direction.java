package be.strijbol.advent.mmxvii.day11;

/**
 * @author Niko Strijbol
 */
enum Direction {
    NORTH(1, -1),
    NORTHEAST(1, 0),
    SOUTHEAST(0, 1),
    SOUTH(-1, 1),
    SOUTHWEST(-1, 0),
    NORTHWEST(0, -1);

    private final int x;
    private final int z;

    Direction(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public Offset getOffset() {
        return new Offset(x, z);
    }

    public static Direction fromString(String string) {
        switch (string) {
            case "n":
                return NORTH;
            case "ne":
                return NORTHEAST;
            case "se":
                return SOUTHEAST;
            case "s":
                return SOUTH;
            case "sw":
                return SOUTHWEST;
            case "nw":
                return NORTHWEST;
            default:
                throw new IllegalArgumentException("The argument " + string + " is invalid.");
        }
    }

    public static class Offset {

        public final int x;
        public final int z;

        private Offset(int x, int z) {
            this.x = x;
            this.z = z;
        }
    }
}
