package solution.instructions;

import solution.Operand;
import solution.Register;
import solution.SharedBuffer;
import solution.SoundCard;

/**
 * @author Niko Strijbol
 */
public class Jgz extends BiOperandInstruction {

    public Jgz(Operand goal, Operand source) {
        super(goal, source);
    }

    @Override
    public long execute(Register register, SoundCard card, SharedBuffer ignored) {
        return goal.resolve(register) > 0 ? source.resolve(register) : 1;
    }
}