package solution.instructions;

import solution.Operand;
import solution.Register;
import solution.SharedBuffer;
import solution.SoundCard;

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