package be.strijbol.advent.mmxix.codes;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Interface for the different instructions.
 *
 * @author Niko Strijbol
 */
public interface Instruction {

    /**
     * Execute the instruction.
     */
    default void execute(Memory memory, Supplier<Long> input, Consumer<Long> output) throws HaltException {
        execute(memory);
    }

    default void execute(Memory memory) throws HaltException {
        throw new UnsupportedOperationException("You must implement one of the 'execute' methods.");
    }

    /**
     * The increment for the instruction pointer. Some instructions can require being executed first, before this
     * function produces meaningful results.
     */
    default int updateInstructionPointer(Memory memory, int previousIp) {
        return previousIp + getLength();
    }

    default int getLength() {
        throw new UnsupportedOperationException("You must implement getLength or updateInstructionPointer");
    }
}
