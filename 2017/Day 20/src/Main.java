import be.almiro.java.advent.coordinate.Coordinate;
import be.almiro.java.advent.coordinate.Distance;
import one.util.streamex.EntryStream;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static final int TICKS = 1000;

    private static List<Point> parse() throws FileNotFoundException {
       List<String> lines = new BufferedReader(new FileReader("Day 20/input/input.txt")).lines().collect(Collectors.toList());
       return EntryStream.of(lines)
               .mapKeyValue(Point::parse)
               .collect(Collectors.toList());
    }

    public static void one() throws FileNotFoundException {
        List<Point> allPoints = parse();
        Point closest = null;
        Comparator<Point> comparator = Comparator.comparing(p -> p.getPosition().distance(Coordinate.ZERO), Distance.manhattanComparator());

        for (int i = 0; i < TICKS; i++) {
            // Execute the ticks.
            allPoints = allPoints.stream().map(Point::tick).sorted(comparator).collect(Collectors.toList());
            // Get closest
            closest = allPoints.get(0);
        }

        System.out.println("Closed ID is: " + closest.getId() + " with pos " + closest.getPosition());
    }

    public static void two() throws FileNotFoundException {
        List<Point> allPoints = parse();

        for (int i = 0; i < TICKS; i++) {
            allPoints = allPoints.stream()
                    .map(Point::tick)
                    .collect(Collectors.groupingBy(Point::getPosition))
                    .entrySet()
                    .stream()
                    .filter(e -> e.getValue().size() == 1)
                    .flatMap(e -> e.getValue().stream())
                    .collect(Collectors.toList());
        }

        System.out.println("Particles left is: " + allPoints.size());
    }

    public static void main(String[] args) throws FileNotFoundException {
        one();
        two();
    }
}
