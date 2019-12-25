package be.strijbol.advent.mmxvii.day18;

import be.strijbol.advent.mmxvii.day18.instructions.Instruction;

import java.util.List;

/**
 * An interpreter, allowing debugging after instructions have been executed.
 *
 * @author Niko Strijbol
 */
public class MultithreadedDebugger {

    public interface Debugger {
        boolean debug(Register register, Instruction instruction, SharedBuffer sharedBuffer);
    }

    private Debugger beforeExecution;
    private Debugger afterExecution;
    private final SoundCard soundCard = new SoundCard();
    private final SharedBuffer buffer = new SharedBuffer();

    public void setAfterExecution(Debugger afterExecution) {
        this.afterExecution = afterExecution;
    }

    public void setBeforeExecution(Debugger beforeExecution) {
        this.beforeExecution = beforeExecution;
    }

    public void execute(List<Instruction> instructions, int programId, Thread.UncaughtExceptionHandler handler) {
        Runnable execution = () -> {
            System.out.println("Starting program " + programId);
            Register register = new Register(programId);
            long i = 0;
            try {
                while (i < instructions.size()) {
                    Instruction instruction = instructions.get(Math.toIntExact(i));
                    if (beforeExecution != null && !beforeExecution.debug(register, instruction, buffer)) {
                        break;
                    }
                    i += instruction.execute(register, soundCard, buffer);
                    if (afterExecution != null && !afterExecution.debug(register, instruction, buffer)) {
                        break;
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException();
            }
        };

        Thread t = new Thread(execution);
        t.setUncaughtExceptionHandler(handler);
        t.start();
    }
}