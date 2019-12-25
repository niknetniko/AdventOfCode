package be.strijbol.advent.mmxvii.day11;

import be.strijbol.advent.common.geometry.WindDirection;

/**
 * @author Niko Strijbol
 */
class Path {

    private final Iterable<WindDirection> directions;
    private final Coordinate start;

    public Path(Iterable<WindDirection> directions, Coordinate start) {
        this.directions = directions;
        this.start = start;
    }

    public Coordinate getStart() {
        return start;
    }

    public Coordinate getEnd() {
        Coordinate current = start;
        for (WindDirection direction: directions) {
            current = current.neighbour(direction);
        }
        return current;
    }
}