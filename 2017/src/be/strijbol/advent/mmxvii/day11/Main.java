package be.strijbol.advent.mmxvii.day11;

import be.strijbol.advent.common.geometry.WindDirection;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static List<WindDirection> read() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Day 11/input/input.txt"));
        return Arrays.stream(reader.readLine().split(","))
                .map(WindDirection::fromString)
                .collect(Collectors.toList());
    }

    private static int one() throws IOException {
        List<WindDirection> directions = read();
        Coordinate start = Coordinate.of(0, 0, 0);
        Path path = start.walk(directions);
        Coordinate end = path.getEnd();
        Distance distance = start.distanceTo(end);
        return distance.asValue();
    }

    private static int two() throws IOException {
        List<WindDirection> directions = read();
        final Coordinate start = Coordinate.of(0, 0, 0);
        Coordinate current = start;
        int furthest = 0;
        for (WindDirection direction: directions) {
            Coordinate newCoordinate = current.neighbour(direction);
            Distance distance = start.distanceTo(newCoordinate);
            int asValue = distance.asValue();
            if (asValue > furthest) {
                furthest = asValue;
            }
            current = newCoordinate;
        }

        return furthest;
    }

    public static void main(String[] args) throws IOException {
        //System.out.println("One is " + one());
        System.out.println("Two is " + two());
    }
}