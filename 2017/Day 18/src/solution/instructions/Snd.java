package solution.instructions;

import solution.Operand;
import solution.Register;
import solution.SharedBuffer;
import solution.SoundCard;

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