package solution.instructions;

import solution.Register;
import solution.SharedBuffer;
import solution.SoundCard;

/**
 * @author Niko Strijbol
 */
@FunctionalInterface
public interface Instruction {

    /**
     * Executes the instruction.
     *
     * @param register The register.
     *
     * @return How many solution.instructions should be skipped. 1 is the next, -1 is the previous and so on.
     */
    long execute(Register register, SoundCard card, SharedBuffer buffer) throws InterruptedException;
}