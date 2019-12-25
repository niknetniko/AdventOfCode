import be.strijbol.advent.common.tuple.Pair;
import be.strijbol.advent.mmxvii.day10.KnotHash;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * @author Niko Strijbol
 */
public class Main {

    public static String padLeftZero(String s, int n) {
        return String.format("%1$" + n + "s", s).replace(' ', '0');
    }

    private static long one() {

        final String BASE = "wenycdww";

        KnotHash hash = new KnotHash(64);

        return LongStream.rangeClosed(0, 127)
                .map(operand -> {
                    List<Integer> lengths = (BASE + "-" + operand).chars().boxed().collect(Collectors.toList());
                    return Stream.of(hash.apply(lengths))
                            .flatMap(s -> Arrays.stream(s.split("")))
                            .map(s -> Integer.parseInt(s, 16))
                            .map(Integer::toBinaryString)
                            .map(s -> padLeftZero(s, 4))
                            .flatMap(s -> Arrays.stream(s.split("")))
                            .map(Integer::parseInt)
                            .filter(i -> i == 1)
                            .count();
                })
                .sum();
    }

    private static long two() {
        final String BASE = "wenycdww";
        KnotHash hash = new KnotHash(64);
        int[][] grid = new int[128][128];


        for (int i = 0; i < 128; i++) {
            List<Integer> lengths = (BASE + "-" + i).chars().boxed().collect(Collectors.toList());
            List<Integer> r = Stream.of(hash.apply(lengths))
                    .flatMap(s -> Arrays.stream(s.split("")))
                    .map(s -> Integer.parseInt(s, 16))
                    .map(Integer::toBinaryString)
                    .map(s -> padLeftZero(s, 4))
                    .flatMap(s -> Arrays.stream(s.split("")))
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            for (int j = 0; j < 128; j++) {
                grid[i][j] = r.get(j);
            }
        }

        Set<Pair<Integer, Integer>> visited = new HashSet<>();
        int counter = 0;

        for (int i = 0; i < 128; i++) {
            for (int j = 0; j < 128; j++) {
                Pair<Integer, Integer> coordinate = Pair.of(i, j);
                if (visited.contains(coordinate)) {
                    continue;
                }
                if (grid[i][j] == 0) {
                    continue;
                }
                counter++;
                dfs(i, j, visited, grid);
            }
        }
        return counter;
    }

    private static void dfs(int i, int j, Set<Pair<Integer, Integer>> seen, final int[][] grid) {
        Pair<Integer, Integer> p = Pair.of(i, j);
        if (seen.contains(p)) {
            return;
        }

        if (grid[i][j] == 0) {
            return;
        }

        // Mark this one.
        seen.add(p);
        // Add neighbours.
        if (i > 0) {
            dfs(i - 1, j, seen, grid);
        }
        if (i < 127) {
            dfs(i + 1, j, seen, grid);
        }
        if (j > 0) {
            dfs(i, j - 1, seen, grid);
        }
        if (j < 127) {
            dfs(i, j + 1, seen, grid);
        }
    }

    public static void main(String[] args) {
        //System.out.println("One is " + one());
        System.out.println("Two is " + two());
    }

}