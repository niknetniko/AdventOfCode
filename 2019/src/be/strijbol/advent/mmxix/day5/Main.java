package be.strijbol.advent.mmxix.day5;

import be.strijbol.advent.common.io.Inputs;
import be.strijbol.advent.mmxix.codes.Computer;

import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.List;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static void execute(long i) throws FileNotFoundException {
        var input = List.of(i);
        var computer = new Computer();
        computer.loadProgram(Inputs.line(2019, 5));
        var out = computer.execute(input);
        System.out.println("Output is: ");
        for (var o: out) {
            System.out.println(o);
        }
    }

    private static void one() throws FileNotFoundException {
        execute(1);
    }

    private static void two() throws FileNotFoundException {
        execute(5);
    }

    public static void main(String[] args) throws FileNotFoundException {
        one();
        two();
    }
}
