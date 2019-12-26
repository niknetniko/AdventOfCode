package be.strijbol.advent.mmxix.codes;

import java.util.Optional;
import java.util.Queue;

/**
 * Interface for the different instructions.
 *
 * @author Niko Strijbol
 */
public interface Instruction {

    /**
     * Execute the instruction.
     */
    Optional<Integer> execute(Memory memory, Queue<Integer> inputs) throws HaltException;

    /**
     * The increment for the instruction pointer. Some instructions can require being executed first, before this
     * function produces meaningful results.
     */
    default int updateInstructionPointer(Memory memory, int previousIp) {
        return previousIp + getLength();
    }

    default int getLength() {
        throw new UnsupportedOperationException("You must implement getLength of updateInstructionPointer");
    }
}
