package be.strijbol.advent.mmxvii.day18.instructions;

import be.strijbol.advent.mmxvii.day18.Operand;
import be.strijbol.advent.mmxvii.day18.Register;
import be.strijbol.advent.mmxvii.day18.SharedBuffer;
import be.strijbol.advent.mmxvii.day18.SoundCard;

/**
 * @author Niko Strijbol
 */
public class ASnd extends SingleOperandInstruction {

    public ASnd(Operand operand) {
        super(operand);
    }

    @Override
    public long execute(Register register, SoundCard card, SharedBuffer buffer) throws InterruptedException {
        buffer.send(register.getOtherId(), operand.resolve(register));
        return 1;
    }
}