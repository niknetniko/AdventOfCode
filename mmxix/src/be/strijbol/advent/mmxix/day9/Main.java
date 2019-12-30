package be.strijbol.advent.mmxix.day9;

import be.strijbol.advent.common.io.Inputs;
import be.strijbol.advent.mmxix.codes.Computer;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static void run(long input) throws FileNotFoundException {
        var program = Inputs.line(2019, 9);
        Computer.run(program, List.of(input));
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Day one is ");
        run(1L);
        System.out.println("Day two is ");
        run(2L);
    }

}
