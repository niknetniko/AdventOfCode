/**
 * @author Niko Strijbol
 */
public class Conditional {

    private final String goal;
    private final Operation operation;
    private final int value;

    public Conditional(String goal, Operation operation, int value) {
        this.goal = goal;
        this.operation = operation;
        this.value = value;
    }

    public void apply(Register register, Instruction instruction) {
        if (operation.compare(register.getValue(goal), value)) {
            int original = register.getValue(instruction.register);
            int newValue = instruction.operation.apply(original, instruction.diff);
            register.setValue(instruction.register, newValue);
        }
    }
}
