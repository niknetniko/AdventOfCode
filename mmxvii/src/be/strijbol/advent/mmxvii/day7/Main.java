package be.strijbol.advent.mmxvii.day7;

import be.strijbol.advent.common.tuple.Pair;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static String one() throws FileNotFoundException {

        BufferedReader reader = new BufferedReader(new FileReader("Day 7/input/input.txt"));

        Map<String, String> holdeeToHolder = new HashMap<>();
        List<String> parents = new ArrayList<>();

        reader.lines().forEach(s -> {
            String[] parts = s.split(" -> ");
            String holder = parts[0].trim().replaceAll("\\([0-9]+\\)", "").trim();
            if (parts.length == 1) {
                parents.add(holder);
            } else {
                String[] holdees = parts[1].split(", ");
                for (String holdee : holdees) {
                    holdeeToHolder.put(holdee, holder);
                }
            }
        });

        String investigation = parents.get(0);
        while (true) {
            if (holdeeToHolder.containsKey(investigation)) {
                investigation = holdeeToHolder.get(investigation);
            } else {
                return investigation;
            }
        }
    }

    private static Pair<Integer, Integer> weightCalc(String parent, Map<String, List<String>> children, Map<String, Integer> weights) {
        if (!children.containsKey(parent)) {
            return Pair.of(weights.get(parent), -1);
        } else {
            int sum = weights.get(parent);
            Map<Integer, Integer> frequencies = new HashMap<>();
            Map<Integer, String> childs = new HashMap<>();
            for (String child : children.get(parent)) {
                Pair<Integer, Integer> w = weightCalc(child, children, weights);
                if (w.getKey() == -1) {
                    return w;
                }
                frequencies.put(w.getKey(), frequencies.getOrDefault(w.getKey(), 0) + 1);
                childs.put(w.getKey(), child);
                sum += w.getKey();
            }
            if (frequencies.size() > 1) {
                Map.Entry<Integer, Integer> min = Collections.min(frequencies.entrySet(), Comparator.comparingInt(Map.Entry::getValue));
                Map.Entry<Integer, Integer> max = Collections.max(frequencies.entrySet(), Comparator.comparingInt(Map.Entry::getValue));
                return Pair.of(-1, weights.get(childs.get(min.getKey())) + (max.getKey() - min.getKey()));
            }
            return Pair.of(sum, -1);
        }
    }

    private static int two() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("Day 7/input/input.txt"));

        Map<String, List<String>> holderToHoldee = new HashMap<>();
        Map<String, String> holdeeToHolder = new HashMap<>();
        List<String> parents = new ArrayList<>();
        Map<String, Integer> weightMap = new HashMap<>();

        reader.lines().forEach(s -> {
            String[] parts = s.split(" -> ");
            String holder = parts[0].trim().replaceAll("\\([0-9]+\\)", "").trim();
            weightMap.put(holder, Integer.parseInt(parts[0].replaceAll("[^0-9]", "")));
            if (parts.length == 1) {
                parents.add(holder);
            } else {
                String[] holdees = parts[1].split(", ");
                holderToHoldee.put(holder, Arrays.asList(holdees));
                for (String holdee : holdees) {
                    holdeeToHolder.put(holdee, holder);
                }
            }
        });

        // We start with the parents.
        String root;

        String investigation = parents.get(0);
        while (true) {
            if (holdeeToHolder.containsKey(investigation)) {
                investigation = holdeeToHolder.get(investigation);
            } else {
                root = investigation;
                break;
            }
        }

        return weightCalc(root, holderToHoldee, weightMap).getValue();
    }

    public static void main(String[] args) throws FileNotFoundException {
        //System.out.println("First is " + one());
        System.out.println("Second is " + two());
    }

}
