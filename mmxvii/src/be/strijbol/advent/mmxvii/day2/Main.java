package be.strijbol.advent.mmxvii.day2;

import one.util.streamex.StreamEx;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author Niko Strijbol
 */
public class Main {

    public static void one() throws FileNotFoundException {
        int result = StreamEx.of(new BufferedReader(new FileReader("mmxvii/src/be/strijbol/advent/mmxvii/day2/input.txt")).lines())
                .map(s -> s.split( "\\s+"))
                .map(strings -> Arrays.stream(strings).map(Integer::parseInt))
                .map(s -> s.collect(Collectors.summarizingInt(Integer::intValue)))
                .map(s -> s.getMax() - s.getMin())
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("The result is " + result);
    }

    public static void two() throws FileNotFoundException {
        int result = StreamEx.of(new BufferedReader(new FileReader("mmxvii/src/be/strijbol/advent/mmxvii/day2/input.txt")).lines())
                .map(s -> s.split( "\\s+"))
                .map(strings -> Arrays.stream(strings).map(Integer::parseInt))
                .map(s -> s.collect(Collectors.toList()))
                .map(integers -> {
                    for (int i = 0; i < integers.size(); i++) {
                        for (int j = 0; j < integers.size(); j++) {
                            if (i != j) {
                                Integer i1 = integers.get(i);
                                Integer i2 = integers.get(j);
                                if (i1 >= i2 && i1 % i2 == 0) {
                                    return StreamEx.of(i1, i2);
                                }
                            }
                        }
                    }
                    return StreamEx.<Integer>empty();
                })
                .map(s -> s.collect(Collectors.summarizingInt(Integer::intValue)))
                .map(s -> s.getMax() / s.getMin())
                .mapToInt(Integer::intValue)
                .sum();

        System.out.println("The result is " + result);
    }

    public static void main(String[] args) throws FileNotFoundException {
        two();

    }

}
