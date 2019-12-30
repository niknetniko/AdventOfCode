package be.strijbol.advent.mmxix.codes;

import java.util.Optional;
import java.util.Queue;

/**
 * @author Niko Strijbol
 */
public class JumpIfTrueInstruction implements Instruction {

    private final Parameter test;
    private final Parameter ip;

    private boolean hasExecuted = false;
    private long memoryContent = 0;
    private long jump = 0;

    public JumpIfTrueInstruction(Parameter test, Parameter ip) {
        this.test = test;
        this.ip = ip;
    }

    @Override
    public void execute(Memory memory) {
        hasExecuted = true;
        memoryContent = test.reduce(memory);
        jump = ip.reduce(memory);
    }

    @Override
    public int updateInstructionPointer(Memory memory, int previousIp) {
        if (!hasExecuted) {
            throw new IllegalStateException("Execute the instruction first!");
        }
        if (memoryContent != 0) {
            return Math.toIntExact(jump);
        } else {
            return previousIp + 3;
        }
    }
}
