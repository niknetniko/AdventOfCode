/**
 * @author Niko Strijbol
 */
public class Path {

    private final Iterable<Direction> directions;
    private final Coordinate start;

    public Path(Iterable<Direction> directions, Coordinate start) {
        this.directions = directions;
        this.start = start;
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        Coordinate current = start;
        for (Direction direction: directions) {
            current = current.neighbour(direction);
        }
        return current;
    }
}