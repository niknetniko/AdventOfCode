package solution.instructions;

import solution.Operand;
import solution.Register;
import solution.SharedBuffer;
import solution.SoundCard;

/**
 * @author Niko Strijbol
 */
public class Rcv extends SingleOperandInstruction {

    public Rcv(Operand operand) {
        super(operand);
    }

    @Override
    public long execute(Register register, SoundCard card, SharedBuffer ignored) {
        long value = operand.resolve(register);
        if (value != 0) {
            System.out.println("Recovered frequency: " + card.getLastFrequency());
        }
        return 1;
    }
}
