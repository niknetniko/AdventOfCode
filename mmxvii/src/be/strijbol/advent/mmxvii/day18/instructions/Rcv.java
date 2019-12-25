package be.strijbol.advent.mmxvii.day18.instructions;

import be.strijbol.advent.mmxvii.day18.Operand;
import be.strijbol.advent.mmxvii.day18.Register;
import be.strijbol.advent.mmxvii.day18.SharedBuffer;
import be.strijbol.advent.mmxvii.day18.SoundCard;

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
