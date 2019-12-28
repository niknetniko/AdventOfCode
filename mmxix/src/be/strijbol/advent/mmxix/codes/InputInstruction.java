package be.strijbol.advent.mmxix.codes;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Niko Strijbol
 */
class InputInstruction implements Instruction {

    private final int address;

    public InputInstruction(int address) {
        this.address = address;
    }

    @Override
    public void execute(Memory memory, Supplier<Integer> input, Consumer<Integer> output) {
        memory.write(address, input.get());
    }

    @Override
    public int getLength() {
        return 2;
    }
}
