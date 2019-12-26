package be.strijbol.advent.mmxix.codes;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Niko Strijbol
 */
public class Computer {

    private Memory memory;

    public void loadProgram(String program) {
        this.memory = new Memory(Arrays.stream(program.split(",")).map(Integer::valueOf).collect(Collectors.toList()));
    }

    public void execute(int input1, int input2) {
        memory.setInput1(input1);
        memory.setInput2(input2);
        execute(new ArrayDeque<>());
    }

    public List<Integer> execute(Queue<Integer> inputs) {
        if (memory == null) {
            throw new IllegalStateException("Program must be loaded before execution.");
        }
        var output = new ArrayList<Integer>();
        int ip = 0;
        try {
            do {
                Instruction instruction = memory.readNext(ip);
                instruction.execute(memory, inputs).ifPresent(output::add);
                ip += instruction.getLength();
            } while (ip < memory.length());
        } catch (HaltException e) {
            // Nothing.
        }
        return output;
    }

    public Memory getMemory() {
        return memory;
    }
}
