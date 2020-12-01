package be.strijbol.advent.mmxix.day3;

import be.strijbol.advent.common.geometry.Coordinate2D;
import be.strijbol.advent.common.tuple.Pair;

import java.util.*;
import java.util.stream.Stream;

/**
 * A virtual grid.
 *
 * @author Niko Strijbol
 */
class StepGrid {

    private final Coordinate2D start = Coordinate2D.ZERO;

    private Map<Coordinate2D, Pair<Map<Integer, Integer>, BitSet>> paths = new HashMap<>();

    private int pathIndex = 0;

    public void add(Path path) {
        pathIndex++;
        int steps = 0;
        for (Coordinate2D coordinate : path.applyTo(start)) {
            var pair = paths.computeIfAbsent(coordinate, c -> Pair.of(new HashMap<>(), new BitSet()));
            pair.getLeft().putIfAbsent(pathIndex, steps++);
            pair.getRight().set(pathIndex, true);
        }
    }

    public Stream<Pair<Coordinate2D, Integer>> getCrossovers() {
        return paths.entrySet().stream()
                .filter(entry -> entry.getValue().getRight().cardinality() > 1)
                .map(entry -> Pair.of(
                        entry.getKey(),
                        entry.getValue().getLeft().values().stream().mapToInt(i -> i + 1).sum())
                );
    }
}
