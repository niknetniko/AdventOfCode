package be.strijbol.advent.mmxvii.day1;

import be.strijbol.advent.common.tuple.Pair;
import one.util.streamex.StreamEx;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void one() throws FileNotFoundException {
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));

        long result = StreamEx.of(new BufferedReader(new FileReader("mmxvii/src/be/strijbol/advent/mmxvii/day1/input.txt")).lines())
                .map(s -> s + s.charAt(0))
                .flatMap(s -> s.chars().boxed())
                .map(integer -> integer - '0')
                .pairMap(Pair::of)
                .filter(p -> p.getValue().equals(p.getKey()))
                .map(Pair::getKey)
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("Result is " + result);
    }

    public static void two() throws FileNotFoundException {
        List<Integer> l = StreamEx.of(new BufferedReader(new FileReader("mmxvii/src/be/strijbol/advent/mmxvii/day1/input.txt")).lines())
                .map(s -> s + s.charAt(0))
                .flatMap(s -> s.chars().boxed())
                .map(integer -> integer - '0')
                .collect(Collectors.toList());

        int steps = l.size() / 2;
        int sum = 0;

        for (int i = 1; i < l.size(); i++) {
            Integer comparing = l.get(i);
            int iNext = (i + steps) % (l.size() - 1);
            if (comparing.equals(l.get(iNext))) {
                sum += comparing;
            }
        }

        System.out.println("Result is " + sum);
    }

    public static void main(String[] args) throws FileNotFoundException {
        one();
        two();
    }
}
