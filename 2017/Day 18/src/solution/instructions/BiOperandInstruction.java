package solution.instructions;

import solution.Operand;

/**
 * @author Niko Strijbol
 */
abstract class BiOperandInstruction implements Instruction {

    final Operand goal;
    final Operand source;

    BiOperandInstruction(Operand goal, Operand source) {
        this.goal = goal;
        this.source = source;
    }

    public Operand getGoal() {
        return goal;
    }

    public Operand getSource() {
        return source;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName().toLowerCase() + " " + goal + " " + source;
    }
}