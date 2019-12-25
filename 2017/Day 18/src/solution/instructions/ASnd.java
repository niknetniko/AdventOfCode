package solution.instructions;

import solution.Operand;
import solution.Register;
import solution.SharedBuffer;
import solution.SoundCard;

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