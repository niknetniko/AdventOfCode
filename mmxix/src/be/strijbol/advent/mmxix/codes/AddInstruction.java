package be.strijbol.advent.mmxix.codes;

/**
 * Interface for the different opcodes.
 *
 * @author Niko Strijbol
 */
class AddInstruction implements Instruction {

    private final Parameter input1;
    private final Parameter input2;
    private final Parameter output;

    public AddInstruction(Parameter input1, Parameter input2, Parameter output) {
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
    }

    @Override
    public void execute(Memory memory) {
        memory.write(output.reduce(memory), input1.reduce(memory) + input2.reduce(memory));
    }

    @Override
    public int getLength() {
        return 4;
    }
}
