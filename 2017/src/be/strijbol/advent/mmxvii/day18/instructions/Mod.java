package be.strijbol.advent.mmxvii.day18.instructions;

import be.strijbol.advent.mmxvii.day18.Operand;
import be.strijbol.advent.mmxvii.day18.Register;
import be.strijbol.advent.mmxvii.day18.SharedBuffer;
import be.strijbol.advent.mmxvii.day18.SoundCard;

/**
 * @author Niko Strijbol
 */
public class Mod extends BiOperandInstruction {

    public Mod(Operand goal, Operand source) {
        super(goal, source);
    }

    @Override
    public long execute(Register register, SoundCard card, SharedBuffer ignored) {
        register.set(goal, goal.resolve(register) % source.resolve(register));
        return 1;
    }
}