package be.strijbol.advent.mmxix.bench;

import be.strijbol.advent.mmxix.codes.Computer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

/**
 * @author Niko Strijbol
 */
public class Main {

    public static String line(String name) throws FileNotFoundException {
        String path = String.format("src/be/strijbol/advent/mmxix/bench/%s", name);
        return new BufferedReader(new FileReader(path)).lines().findFirst().orElseThrow();
    }

    public static void main(String[] args) throws FileNotFoundException {
        long startTime = System.nanoTime();
        Computer.run(line("program.txt"), List.of(2147483647L));
        long endTime = System.nanoTime();
        System.out.println("Time was " + ((endTime - startTime) / 1_000_000) + "ms");
    }
}
