package be.strijbol.advent.mmxix.codes;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Niko Strijbol
 */
class OutputInstruction implements Instruction {

    private final Parameter address;

    public OutputInstruction(Parameter address) {
        this.address = address;
    }

    @Override
    public void execute(Memory memory, Supplier<Long> input, Consumer<Long> output) {
        output.accept(address.reduce(memory));
    }

    @Override
    public int getLength() {
        return 2;
    }
}
