package be.strijbol.advent.mmxix.codes;

import java.util.Optional;
import java.util.Queue;

/**
 * @author Niko Strijbol
 */
public class EqualsInstruction implements Instruction {

    private final Parameter a;
    private final Parameter b;
    private final int c;

    public EqualsInstruction(Parameter a, Parameter b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public Optional<Integer> execute(Memory memory, Queue<Integer> inputs) {
        if (a.reduce(memory) == b.reduce(memory)) {
            memory.write(c, 1);
        } else {
            memory.write(c, 0);
        }
        return Optional.empty();
    }

    @Override
    public int getLength() {
        return 4;
    }
}
