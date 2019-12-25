package solution.instructions;

import solution.Operand;
import solution.Register;
import solution.SharedBuffer;
import solution.SoundCard;

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