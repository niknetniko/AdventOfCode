package solution.instructions;

import solution.Operand;

/**
 * @author Niko Strijbol
 */
abstract class SingleOperandInstruction implements Instruction {

    protected final Operand operand;

    SingleOperandInstruction(Operand operand) {
        this.operand = operand;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName().toLowerCase() + " " + operand;
    }

    public Operand getOperand() {
        return operand;
    }
}