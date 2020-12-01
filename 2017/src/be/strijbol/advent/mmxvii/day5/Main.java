package be.strijbol.advent.mmxvii.day5;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static long one() throws FileNotFoundException {
        // Read steps as integers.
        BufferedReader reader = new BufferedReader(new FileReader("mmxvii/src/be/strijbol/advent/mmxvii/day5/input.txt"));

        List<Integer> jumps = reader.lines()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        long counter = 0;
        int currentIndex = 0;
        try {
            while (true) {
                int jump = jumps.get(currentIndex);
                jumps.set(currentIndex, jump + 1);
                currentIndex += jump;
                counter++;
            }
        } catch (IndexOutOfBoundsException e) {
            return counter;
        }
    }

    private static long two() throws FileNotFoundException {
        // Read steps as integers.
        BufferedReader reader = new BufferedReader(new FileReader("mmxvii/src/be/strijbol/advent/mmxvii/day5/input.txt"));

        List<Integer> jumps = reader.lines()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        long counter = 0;
        int currentIndex = 0;
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                int jump = jumps.get(currentIndex);
                if (jump >= 3) {
                    jumps.set(currentIndex, jump - 1);
                } else {
                    jumps.set(currentIndex, jump + 1);
                }
                currentIndex += jump;
                counter++;
            }
        } catch (IndexOutOfBoundsException e) {
            return counter;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        //System.out.println("Nr of jump is " + one());
        long startTime = System.nanoTime();
        System.out.println("Nr of jump is " + two());
        long endTime = System.nanoTime();

        long duration = ((endTime - startTime) / 1_000_000);
        System.out.println(duration + "ms");
    }

}
