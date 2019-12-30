package be.strijbol.advent.mmxix.codes;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Niko Strijbol
 */
class InputInstruction implements Instruction {

    private final Parameter address;

    public InputInstruction(Parameter address) {
        this.address = address;
    }

    @Override
    public void execute(Memory memory, Supplier<Long> input, Consumer<Long> output) {
        memory.write(address.reduce(memory), input.get());
    }

    @Override
    public int getLength() {
        return 2;
    }
}
