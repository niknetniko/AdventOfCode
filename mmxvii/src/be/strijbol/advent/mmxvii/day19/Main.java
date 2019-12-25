package be.strijbol.advent.mmxvii.day19;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static Grid parse() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("Day 19/input/input.txt"));
        List<List<Character>> lines = reader.lines()
                .map(s -> s.chars().mapToObj(c -> (char) c).collect(Collectors.toList()))
                .collect(Collectors.toList());

        char[][] chars = new char[lines.size()][];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = new char[lines.get(i).size()];
            for (int j = 0; j < chars[i].length; j++) {
                chars[i][j] = lines.get(i).get(j);
            }
        }
        return new Grid(chars);
    }

    private static Map.Entry<Integer, Integer> getNext(Direction direction, Map.Entry<Integer, Integer> c) {
        switch (direction) {
            case LEFT:
                return Map.entry(c.getKey() - 1, c.getValue());
            case UP:
                return Map.entry(c.getKey(), c.getValue() - 1);
            case DOWN:
                return Map.entry(c.getKey(), c.getValue() + 1);
            case RIGHT:
                return Map.entry(c.getKey() + 1, c.getValue());
            default:
                throw new IllegalArgumentException();
        }
    }

    private static boolean isValid(Grid grid, Map.Entry<Integer, Integer> c) {
        return c.getKey() >= 0 && c.getKey() < grid.getX() && c.getValue() >= 0 && c.getValue() < grid.getY();
    }

    private static void follow() throws FileNotFoundException {
        Grid grid = parse();
        StringBuilder encounteredWords = new StringBuilder();

        Map.Entry<Integer, Integer> c = null;
        Direction previous = Direction.DOWN;
        // Find the start position.
        for (int x = 0; x < grid.getX(); x++) {
            if (grid.isPath(Map.entry(x, 0))) {
                c = Map.entry(x, 0);
                break;
            }
        }

        if (c == null) {
            throw new IllegalArgumentException("Input is invalid.");
        }

        // Start following the line.
        boolean ended = false;
        int counter = 0;
        while (!ended) {

            // The current position must be a line.
            assert grid.isPath(c);

            // Record the letter
            if (grid.isLetter(c)) {
                encounteredWords.append(grid.get(c));
            }

            // If the current is horizontal, vertical or a letter, we must continue or we have ended.
            if (grid.isHorizontal(c) || grid.isVertical(c) || grid.isLetter(c)) {
                c = getNext(previous, c);
                counter++;
                if (!isValid(grid, c) || grid.isEmpty(c)) {
                    // We ended.
                    ended = true;
                }
            } else {
                assert grid.isCrossRoad(c);

                List<Map.Entry<Map.Entry<Integer, Integer>, Direction>> pairs = List.of(
                        Map.entry(Map.entry(c.getKey() - 1, c.getValue()), Direction.LEFT),
                        Map.entry(Map.entry(c.getKey() + 1, c.getValue()), Direction.RIGHT),
                        Map.entry(Map.entry(c.getKey(), c.getValue() - 1), Direction.UP),
                        Map.entry(Map.entry(c.getKey(), c.getValue() + 1), Direction.DOWN)
                );
                for (Map.Entry<Map.Entry<Integer, Integer>, Direction> pair : pairs) {
                    if (isValid(grid, pair.getKey())) {
                        if (!pair.getKey().equals(getNext(previous.getReverse(), c))) {
                            if (grid.isPath(pair.getKey())) {
                                c = pair.getKey();
                                previous = pair.getValue();
                                counter++;
                                break;
                            }
                        }
                    }
                }

            }
        }

        System.out.println("Encountered letters are: "+ encounteredWords.toString());
        System.out.println("Steps taken is: " + counter);
    }

    private enum Direction {
        LEFT, RIGHT, UP, DOWN;

        public Direction getReverse() {
            switch (this) {
                case RIGHT:
                    return LEFT;
                case DOWN:
                    return UP;
                case UP:
                    return DOWN;
                case LEFT:
                    return RIGHT;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        follow();
    }
}
