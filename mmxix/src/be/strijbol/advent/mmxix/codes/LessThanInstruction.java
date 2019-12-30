package be.strijbol.advent.mmxix.codes;

/**
 * @author Niko Strijbol
 */
public class LessThanInstruction implements Instruction {

    private final Parameter a;
    private final Parameter b;
    private final Parameter output;

    public LessThanInstruction(Parameter a, Parameter b, Parameter output) {
        this.a = a;
        this.b = b;
        this.output = output;
    }

    @Override
    public void execute(Memory memory) {
        if (a.reduce(memory) < b.reduce(memory)) {
            memory.write(output.reduce(memory), 1);
        } else {
            memory.write(output.reduce(memory), 0);
        }
    }

    @Override
    public int getLength() {
        return 4;
    }
}
