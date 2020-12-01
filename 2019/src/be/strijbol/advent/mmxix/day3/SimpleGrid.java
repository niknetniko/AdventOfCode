package be.strijbol.advent.mmxix.day3;

import be.strijbol.advent.common.geometry.Coordinate2D;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * A virtual grid.
 *
 * @author Niko Strijbol
 */
class SimpleGrid {

    private final Coordinate2D start = Coordinate2D.ZERO;

    private Map<Coordinate2D, BitSet> paths = new HashMap<>();

    private int pathIndex = 0;

    public void add(Path path) {
        pathIndex++;
        for (Coordinate2D coordinate : path.applyTo(start)) {
            paths.computeIfAbsent(coordinate, c -> new BitSet()).set(pathIndex, true);
        }
    }

    public Stream<Coordinate2D> getCrossovers() {
        return paths.entrySet().stream()
                .filter(entry -> entry.getValue().cardinality() > 1)
                .map(Map.Entry::getKey);
    }

    public Coordinate2D getStart() {
        return start;
    }
}
