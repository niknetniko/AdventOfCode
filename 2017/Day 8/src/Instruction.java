/**
 * @author Niko Strijbol
 */
class Instruction {

    final String register;
    final Command operation;
    final int diff;
    final Conditional conditional;

    Instruction(String register, Command operation, int diff, Conditional conditional) {
        this.register = register;
        this.operation = operation;
        this.diff = diff;
        this.conditional = conditional;
    }
}
