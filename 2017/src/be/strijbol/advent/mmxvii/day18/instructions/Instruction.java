package be.strijbol.advent.mmxvii.day18.instructions;

import be.strijbol.advent.mmxvii.day18.Register;
import be.strijbol.advent.mmxvii.day18.SharedBuffer;
import be.strijbol.advent.mmxvii.day18.SoundCard;

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