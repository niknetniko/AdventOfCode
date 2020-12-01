package be.strijbol.advent.mmxvii.day18.instructions;

import be.strijbol.advent.mmxvii.day18.Operand;
import be.strijbol.advent.mmxvii.day18.Register;
import be.strijbol.advent.mmxvii.day18.SharedBuffer;
import be.strijbol.advent.mmxvii.day18.SoundCard;

/**
 * @author Niko Strijbol
 */
public class Add extends BiOperandInstruction {

    public Add(Operand goal, Operand source) {
        super(goal, source);
    }

    @Override
    public long execute(Register register, SoundCard card, SharedBuffer ignored) {
        long original = goal.resolve(register);
        register.set(goal, original + source.resolve(register));
        return 1;
    }
}