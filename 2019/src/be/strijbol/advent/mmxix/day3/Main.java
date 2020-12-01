package be.strijbol.advent.mmxix.day3;

import be.strijbol.advent.common.geometry.Distance;
import be.strijbol.advent.common.io.Inputs;
import be.strijbol.advent.common.tuple.Pair;

import java.io.FileNotFoundException;
import java.util.Comparator;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static long one() throws FileNotFoundException {
        var grid = new SimpleGrid();
        Inputs.lines(2019, 3)
                .map(Path::fromString)
                .forEach(grid::add);

        // Get point closest to start.
        var min = grid.getCrossovers()
                .map(c -> c.distance(grid.getStart()))
                .min(Distance.manhattanComparator());

        return min.orElseThrow().manhattan();
    }

    private static long two() throws FileNotFoundException {
        var grid = new StepGrid();
        Inputs.lines(2019, 3)
                .map(Path::fromString)
                .forEach(grid::add);

        // Get point closest to start.
        var min = grid.getCrossovers()
                .min(Comparator.comparingInt(Pair::getRight));

        return min.orElseThrow().getRight();
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("One is " + one());
        System.out.println("Two is " + two());
    }

}
