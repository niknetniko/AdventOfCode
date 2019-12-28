package be.strijbol.advent.mmxix.codes;

import java.util.Optional;
import java.util.Queue;

/**
 * @author Niko Strijbol
 */
public class JumpIfFalseInstruction implements Instruction {

    private final Parameter test;
    private final Parameter ip;

    private boolean hasExecuted = false;
    private int memoryContent = 0;
    private int jump = 0;

    public JumpIfFalseInstruction(Parameter test, Parameter ip) {
        this.test = test;
        this.ip = ip;
    }

    @Override
    public Optional<Integer> execute(Memory memory, Queue<Integer> inputs) {
        hasExecuted = true;
        memoryContent = test.reduce(memory);
        jump = ip.reduce(memory);
        return Optional.empty();
    }

    @Override
    public int updateInstructionPointer(Memory memory, int previousIp) {
        if (!hasExecuted) {
            throw new IllegalStateException("Execute the instruction first!");
        }
        if (memoryContent == 0) {
            return jump;
        } else {
            return previousIp + 3;
        }
    }
}