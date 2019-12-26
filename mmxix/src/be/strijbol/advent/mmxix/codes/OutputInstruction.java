package be.strijbol.advent.mmxix.codes;

import java.util.Optional;
import java.util.Queue;

/**
 * @author Niko Strijbol
 */
class OutputInstruction implements Instruction {

    private final Parameter address;

    public OutputInstruction(Parameter address) {
        this.address = address;
    }

    @Override
    public Optional<Integer> execute(Memory memory, Queue<Integer> inputs) {
        return Optional.of(address.reduce(memory));
    }

    @Override
    public int getLength() {
        return 2;
    }
}
