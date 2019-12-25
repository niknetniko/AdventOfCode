package be.strijbol.advent.mmxvii.day18;

import be.strijbol.advent.mmxvii.day18.instructions.Instruction;

import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/**
 * An interpreter, allowing debugging after instructions have been executed.
 *
 * @author Niko Strijbol
 */
public class DebuggableInterpreter implements Consumer<List<Instruction>> {

    private BiPredicate<Register, Instruction> beforeExecution;
    private BiPredicate<Register, Instruction> afterExecution;
    private final Register register = new Register();
    private final SoundCard soundCard = new SoundCard();

    public void setAfterExecution(BiPredicate<Register, Instruction> afterExecution) {
        this.afterExecution = afterExecution;
    }

    public void setBeforeExecution(BiPredicate<Register, Instruction> beforeExecution) {
        this.beforeExecution = beforeExecution;
    }

    @Override
    public void accept(List<Instruction> instructions) {
        register.clear();
        long i = 0;
        try {
            while (i < instructions.size()) {
                Instruction instruction = instructions.get(Math.toIntExact(i));
                if (beforeExecution != null && !beforeExecution.test(register, instruction)) {
                    break;
                }
                i += instruction.execute(register, soundCard, null);
                if (afterExecution != null && !afterExecution.test(register, instruction)) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}