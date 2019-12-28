package be.strijbol.advent.mmxix.codes;

import java.util.Optional;
import java.util.Queue;

/**
 * @author Niko Strijbol
 */
public class LessThanInstruction implements Instruction {

    private final Parameter a;
    private final Parameter b;
    private final int c;

    public LessThanInstruction(Parameter a, Parameter b, int c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    @Override
    public void execute(Memory memory) {
        if (a.reduce(memory) < b.reduce(memory)) {
            memory.write(c, 1);
        } else {
            memory.write(c, 0);
        }
    }

    @Override
    public int getLength() {
        return 4;
    }
}
