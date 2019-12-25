package solution.instructions;

import solution.Operand;
import solution.Register;
import solution.SharedBuffer;
import solution.SoundCard;

/**
 * @author Niko Strijbol
 */
public class Mul extends BiOperandInstruction {

    public  Mul(Operand goal, Operand source) {
        super(goal, source);
    }

    @Override
    public long execute(Register register, SoundCard card, SharedBuffer ignored) {
        register.set(goal, goal.resolve(register) * source.resolve(register));
        return 1;
    }
}
