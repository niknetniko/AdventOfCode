package be.strijbol.advent.mmxvii.day18.instructions;

import be.strijbol.advent.mmxvii.day18.Operand;

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