package solution.instructions;

import solution.Operand;
import solution.Register;
import solution.SharedBuffer;
import solution.SoundCard;

/**
 * @author Niko Strijbol
 */
public class Set extends BiOperandInstruction {

    public Set(Operand goal, Operand source) {
        super(goal, source);
    }

    @Override
    public long execute(Register register, SoundCard card, SharedBuffer buffer) {
        register.set(goal, source.resolve(register));
        return 1;
    }
}