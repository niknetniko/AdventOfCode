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
     * Execute the opcode.
     */
    Optional<Integer> execute(Memory memory, Queue<Integer> inputs) throws HaltException;

    int getLength();
}
