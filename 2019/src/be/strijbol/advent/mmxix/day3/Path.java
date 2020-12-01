package be.strijbol.advent.mmxix.day3;

import be.strijbol.advent.common.geometry.Coordinate;
import be.strijbol.advent.common.geometry.Coordinate2D;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * A wire path.
 *
 * @author Niko Strijbol
 */
class Path {

    private final List<Movement> movements;

    Path(List<Movement> movements) {
        this.movements = movements;
    }

    public static Path fromString(String path) {
        return new Path(Arrays.stream(path.split(","))
                .map(Movement::from)
                .collect(Collectors.toList()));
    }

    public List<Coordinate2D> applyTo(Coordinate2D coordinate) {
        // TODO: this is too complicated for an iterator unfortunately.
        var list = new ArrayList<Coordinate2D>();
        var previous = coordinate;
        for (var movement: movements) {
            for (int i = 0; i < movement.getAmount(); i++) {
                previous = movement.getDirection().plus(previous);
                list.add(previous);
            }
        }
        return list;
    }
}
