package be.strijbol.advent.mmxix.day2;

import be.strijbol.advent.common.collections.CombinationsIterator;
import be.strijbol.advent.common.io.Inputs;
import be.strijbol.advent.common.tuple.Pair;
import be.strijbol.advent.mmxix.codes.Computer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static String read() throws FileNotFoundException {
        return Inputs.lines(2019, 2).findFirst().orElseThrow();
    }

    static int one() throws FileNotFoundException {
        var computer = new Computer();
        computer.loadProgram(read());
        computer.execute(12, 2);
        return computer.getMemory().output();
    }



    static int two() throws FileNotFoundException {
        var computer = new Computer();
        var program = read();
        var inputs1 = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        var inputs2 = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        Iterable<Pair<Integer, Integer>> combining = () -> new CombinationsIterator<>(inputs1, inputs2);
        List<Pair<Integer, Integer>> all = new ArrayList<>();

        for (var pair : combining) {
            computer.loadProgram(program);
            computer.execute(pair.getLeft(), pair.getRight());
            if (computer.getMemory().output() == 19690720) {
                return pair.getLeft() * 100 + pair.getRight();
            }
        }

        throw new IllegalStateException("Correct input was not found.");
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("One is " + one());
        System.out.println("Two is " + two());
    }
}
