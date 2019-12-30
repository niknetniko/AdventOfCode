package be.strijbol.advent.mmxix.codes;

import be.strijbol.advent.common.collections.Lists;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author Niko Strijbol
 */
public class Computer {

    private Memory memory;
    private int ip;

    public void loadProgram(String program) {
        this.ip = 0;
        this.memory = new Memory(Arrays.stream(program.split(",")).map(Long::valueOf).collect(Collectors.toList()));
    }

    public void execute(int input1, int input2) {
        memory.setInput1(input1);
        memory.setInput2(input2);
        execute(Collections.emptyList());
    }

    public List<Long> execute(List<Long> inputs) {
        var input = Lists.asSupplier(inputs);
        final var collected = new ArrayList<Long>();
        execute(input, collected::add);
        return collected;
    }

    public void execute(Supplier<Long> input, Consumer<Long> output) {
        // To prevent infinite loops, we must increment ip before executing the output.
        var out = new DelayedOutput(output);

        if (memory == null) {
            throw new IllegalStateException("Program must be loaded before execution.");
        }
        try {
            //noinspection InfiniteLoopStatement
            while (true) {
                Instruction instruction = memory.readNext(ip);
                instruction.execute(memory, input, out);
                ip = instruction.updateInstructionPointer(memory, ip);
                // Do the actual output now.
                out.doSupply();
                if (ip >= memory.length()) {
                    throw new IllegalStateException("Reading out of memory!");
                }
            }
        } catch (HaltException e) {
            // Nothing.
        }
    }

    public static void repl(String program) {
        var pc = new Computer();
        var input = new Scanner(System.in);
        pc.loadProgram(program);
        pc.execute(input::nextLong, System.out::println);
    }

    public Memory getMemory() {
        return memory;
    }

    public boolean hasProgram() {
        return memory != null;
    }

    private static class DelayedOutput implements Consumer<Long> {

        private final Consumer<Long> nested;
        private final List<Long> collected = new ArrayList<>();

        private DelayedOutput(Consumer<Long> nested) {
            this.nested = nested;
        }

        @Override
        public void accept(Long integer) {
            collected.add(integer);
        }

        public void doSupply() {
            collected.forEach(nested);
        }
    }
}
