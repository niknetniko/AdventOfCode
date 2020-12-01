package be.strijbol.advent.mmxvii.day18.instructions;

import be.strijbol.advent.mmxvii.day18.Operand;
import be.strijbol.advent.mmxvii.day18.Register;
import be.strijbol.advent.mmxvii.day18.SharedBuffer;
import be.strijbol.advent.mmxvii.day18.SoundCard;

/**
 * @author Niko Strijbol
 */
public class Snd extends SingleOperandInstruction {

    public Snd(Operand operand) {
        super(operand);
    }

    @Override
    public long execute(Register register, SoundCard card, SharedBuffer buffer) {
        card.playSound(operand.resolve(register));
        return 1;
    }
}