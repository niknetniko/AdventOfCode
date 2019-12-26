package be.strijbol.advent.mmxix.codes;

import java.util.Optional;
import java.util.Queue;

/**
 * @author Niko Strijbol
 */
class InputInstruction implements Instruction {

    private final int address;

    public InputInstruction(int address) {
        this.address = address;
    }

    @Override
    public Optional<Integer> execute(Memory memory, Queue<Integer> inputs) {
        memory.write(address, inputs.remove());
        return Optional.empty();
    }

    @Override
    public int getLength() {
        return 2;
    }
}
