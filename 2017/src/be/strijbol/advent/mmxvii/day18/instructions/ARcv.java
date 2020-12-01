package be.strijbol.advent.mmxvii.day18.instructions;

import be.strijbol.advent.mmxvii.day18.Operand;
import be.strijbol.advent.mmxvii.day18.Register;
import be.strijbol.advent.mmxvii.day18.SharedBuffer;
import be.strijbol.advent.mmxvii.day18.SoundCard;

/**
 * @author Niko Strijbol
 */
public class ARcv extends SingleOperandInstruction {

    public ARcv(Operand operand) {
        super(operand);
    }

    @Override
    public long execute(Register register, SoundCard card, SharedBuffer buffer) throws InterruptedException {
        long value = buffer.read(register.getProgramId());
        register.set(operand, value);
        return 1;
    }
}