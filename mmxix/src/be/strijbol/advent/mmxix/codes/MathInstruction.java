package be.strijbol.advent.mmxix.codes;

import java.util.Map;
import java.util.function.IntBinaryOperator;

/**
 * Interface for the different opcodes.
 *
 * @author Niko Strijbol
 */
class MathInstruction implements Instruction {

    private int opcode;
    private int input1;
    private int input2;
    private int output;

    public Map<Integer, IntBinaryOperator> actionsMap = Map.of(
            1, Integer::sum,
            2, (x, y) -> x * y
    );

    public MathInstruction(int opcode, int input1, int input2, int output) {
        this.opcode = opcode;
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
    }

    @Override
    public void execute(Memory memory) {
        IntBinaryOperator action = actionsMap.get(opcode);
        int leftOperand = memory.read(input1);
        int rightOperand = memory.read(input2);
        int result = action.applyAsInt(leftOperand, rightOperand);
        memory.write(output, result);
    }

    @Override
    public int getLength() {
        return 4;
    }
}
