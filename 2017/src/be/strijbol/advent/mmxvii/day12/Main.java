package be.strijbol.advent.mmxvii.day12;

import be.strijbol.advent.common.tuple.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static int one() throws FileNotFoundException {
        Map<Integer, Collection<Integer>> graph = readData();

        Set<Integer> visited = new HashSet<>(List.of(0));
        Queue<Integer> queue = new LinkedList<>(graph.getOrDefault(0, Collections.emptyList()));
        while (!queue.isEmpty()) {
            int node = queue.remove();
            // Count and mark
            if (visited.add(node)) {
                queue.addAll(graph.getOrDefault(node, Collections.emptyList()));
            }
        }

        return visited.size();
    }

    private static int two() throws FileNotFoundException {
        Map<Integer, Collection<Integer>> graph = readData();

        Set<Integer> all = new HashSet<>();
        for (Map.Entry<Integer, Collection<Integer>> entry: graph.entrySet()) {
            all.add(entry.getKey());
            all.addAll(entry.getValue());
        }

        int numberOfGroups = 0;
        while (!all.isEmpty()) {
            int startNode = all.iterator().next();
            Set<Integer> visited = new HashSet<>(List.of(startNode));
            Queue<Integer> queue = new LinkedList<>(graph.getOrDefault(startNode, Collections.emptyList()));
            while (!queue.isEmpty()) {
                int node = queue.remove();
                // Count and mark
                if (visited.add(node)) {
                    queue.addAll(graph.getOrDefault(node, Collections.emptyList()));
                }
            }
            all.removeAll(visited);
            numberOfGroups++;
        }

        return numberOfGroups;
    }

    private static Map<Integer, Collection<Integer>> readData() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("Day 12/input/input.txt"));
        return reader.lines()
                .map(s -> {
                    String[] parts = s.split(" <-> ");
                    int one = Integer.parseInt(parts[0]);
                    List<Integer> neighours = Arrays.stream(parts[1].split(", "))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());
                    return Pair.of(one, neighours);
                })
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    public static void main(String[] args) throws FileNotFoundException {
        //System.out.println("One is " + one());
        System.out.println("Two is " + two());
    }
}
