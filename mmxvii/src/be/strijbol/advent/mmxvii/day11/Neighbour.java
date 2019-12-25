package be.strijbol.advent.mmxvii.day11;

/**
 * @author Niko Strijbol
 */
class Neighbour extends Coordinate {

    private final Coordinate base;
    private final Direction.Offset direction;

    public Neighbour(Coordinate base, Direction direction) {
        this.base = base;
        this.direction = direction.getOffset();
    }

    @Override
    public int getX() {
        return base.getX() + direction.x;
    }

    @Override
    public int getZ() {
        return base.getZ() + direction.z;
    }
}