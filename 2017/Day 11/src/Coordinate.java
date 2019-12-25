/**
 * @author Niko Strijbol
 */
public abstract class Coordinate {

    abstract int getX();
    abstract int getZ();

    public int getY() {
        return -getX() - getZ();
    }

    static Coordinate of(int x, int y, int z) {
        return new BaseCoordinate(x, z);
    }

    public Coordinate neighbour(Direction direction) {
        return new Neighbour(this, direction);
    }

    public Path walk(Iterable<Direction> directions) {
        return new Path(directions, this);
    }

    public Distance distanceTo(Coordinate coordinate) {
        return new Distance(this, coordinate);
    }

    @Override
    public String toString() {
        return "(" + getX() + "," + getY() + "," + getZ() + ")";
    }
}