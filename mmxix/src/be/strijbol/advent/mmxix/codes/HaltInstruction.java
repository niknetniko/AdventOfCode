package be.strijbol.advent.mmxix.codes;

import java.util.Optional;
import java.util.Queue;

/**
 * Halt instruction; halts the program execution.
 */
class HaltInstruction implements Instruction {

    public HaltInstruction() {
    }

    @Override
    public Optional<Integer> execute(Memory memory, Queue<Integer> integers) throws HaltException {
        throw new HaltException();
    }

    @Override
    public int getLength() {
        return 1;
    }
}
