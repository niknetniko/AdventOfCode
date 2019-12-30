package be.strijbol.advent.mmxix.day7;

import be.strijbol.advent.common.collections.Lists;
import be.strijbol.advent.common.collections.PermutationIterator;
import be.strijbol.advent.common.io.Inputs;
import be.strijbol.advent.common.util.CollectingConsumer;
import be.strijbol.advent.mmxix.codes.Computer;
import be.strijbol.advent.mmxix.codes.HaltException;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static long one() throws FileNotFoundException {
        var program = Inputs.line(2019, 7);
        var computer = new Computer();
        // There are 5 thrusters, so try every combination without repetition for 0-4.
        var possibleValues = List.of(0L, 1L, 2L, 3L, 4L);
        return StreamSupport.stream(PermutationIterator.spliterator(possibleValues), false)
                .mapToLong(inputs -> {
                    var input = 0L;
                    for (var phase : inputs) {
                        var out = new CollectingConsumer<Long>(false);
                        computer.loadProgram(program);
                        computer.execute(Lists.asSupplier(List.of(phase, input)), out);
                        input = out.getElement().orElseThrow();
                    }
                    return input;
                })
                .max().orElse(0);
    }

    private static long executeFeedbackLoop(List<Long> phases) {
        var amplifiers = new ArrayList<Amplifier>();
        String program;
        try {
            program = Inputs.line(2019, 7);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e); // Prevent errors.
        }
        Consumer<Long> noOutput = integer -> { throw new HaltException(); };
        // Set the phases.
        Amplifier previous = null;
        for (var phase : phases) {
            var computer = new Computer();
            computer.loadProgram(program);
            var amplifier = new Amplifier(phase.toString(), computer);
            amplifier.setOutput(noOutput);
            amplifier.accept(phase);
            if (previous != null) {
                previous.setOutput(amplifier);
            }
            previous = amplifier;
            amplifiers.add(amplifier);
        }

        var finalResult = new CollectingConsumer<Long>(true);
        var last = amplifiers.get(amplifiers.size() - 1);
        var first = amplifiers.get(0);
        Consumer<Long> thrusterAndCallback = integer -> {
            finalResult.accept(integer);
            first.accept(integer);
        };
        last.setOutput(thrusterAndCallback);
        first.accept(0L);
        return finalResult.getElement().orElseThrow();
    }

    private static long two() {
        var possibleValues = List.of(5L, 6L, 7L, 8L, 9L);
        return StreamSupport.stream(PermutationIterator.spliterator(possibleValues), false)
                .mapToLong(Main::executeFeedbackLoop)
                .max()
                .orElseThrow();
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Day 1 is " + one());
        System.out.println("Day 2 is " + two());
    }

}
