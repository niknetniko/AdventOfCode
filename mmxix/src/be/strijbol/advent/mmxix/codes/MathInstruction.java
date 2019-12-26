package be.strijbol.advent.mmxix.codes;

import java.util.Map;
import java.util.Optional;
import java.util.Queue;
import java.util.function.IntBinaryOperator;

/**
 * Interface for the different opcodes.
 *
 * @author Niko Strijbol
 */
class MathInstruction implements Instruction {

    private final int opcode;
    private final Parameter input1;
    private final Parameter input2;
    private final int output;

    public Map<Integer, IntBinaryOperator> actionsMap = Map.of(
            1, Integer::sum,
            2, (x, y) -> x * y
    );

    public MathInstruction(int opcode, Parameter input1, Parameter input2, int output) {
        this.opcode = opcode;
        this.input1 = input1;
        this.input2 = input2;
        this.output = output;
    }

    @Override
    public Optional<Integer> execute(Memory memory, Queue<Integer> inputs) {
        IntBinaryOperator action = actionsMap.get(opcode);
        int leftOperand = input1.reduce(memory);
        int rightOperand = input2.reduce(memory);
        int result = action.applyAsInt(leftOperand, rightOperand);
        memory.write(output, result);
        return Optional.empty();
    }

    @Override
    public int getLength() {
        return 4;
    }
}
