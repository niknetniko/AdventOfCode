package be.strijbol.advent.mmxix.codes;

import be.strijbol.advent.common.tuple.Pair;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;

/**
 * The memory of the computer.
 *
 * @author Niko Strijbol
 */
public class Memory {

    private final List<Integer> values;

    Memory(List<Integer> values) {
        this.values = new ArrayList<>(values);
    }

    /**
     * Read the next instruction in memory at the given instruction pointer.
     *
     * @param ip Where to read the next instruction (the instruction pointer).
     * @return The instruction.
     */
    public Instruction readNext(int ip) {
        var pair = parseOpcode(this.values.get(ip));
        int opcode = pair.getLeft();
        var modes = pair.getRight();
        return switch (opcode) {
            case 99 -> new HaltInstruction();
            case 8 -> new EqualsInstruction(param(ip, modes, 1), param(ip, modes, 2), values.get(ip + 3));
            case 7 -> new LessThanInstruction(param(ip, modes, 1), param(ip, modes, 2), values.get(ip + 3));
            case 6 -> new JumpIfFalseInstruction(param(ip, modes, 1), param(ip, modes, 2));
            case 5 -> new JumpIfTrueInstruction(param(ip, modes, 1), param(ip, modes, 2));
            case 4 -> new OutputInstruction(param(ip, modes, 1));
            case 3 -> new InputInstruction(values.get(ip + 1));
            case 2 -> new MultiplyInstruction(param(ip, modes, 1), param(ip, modes, 2), values.get(ip + 3));
            case 1 -> new AddInstruction(param(ip, modes, 1), param(ip, modes, 2), values.get(ip + 3));
            default -> throw new IllegalArgumentException("Unknown opcode " + opcode);
        };
    }

    private Parameter param(int ip, List<ParameterMode> modes, int number) {
        return new Parameter(values.get(ip + number), modes.get(number - 1));
    }

    private Pair<Integer, List<ParameterMode>> parseOpcode(int code) {
        var asString = String.format("%04d", code);
        var opcode = Integer.valueOf(asString.substring(asString.length() - 2));
        var others = asString.substring(0, asString.length() - 2).chars()
                .map(operand -> Integer.parseInt(Character.toString(operand)))
                .mapToObj(ParameterMode::get)
                .collect(Collectors.toList());
        Collections.reverse(others);
        return Pair.of(opcode, others);
    }

    public int output() {
        return read(0);
    }

    public void setInput1(int input1) {
        write(1, input1);
    }

    public void setInput2(int input2) {
        write(2, input2);
    }

    public int length() {
        return values.size();
    }

    void write(int address, int value) {
        this.values.set(address, value);
    }

    int read(int address) {
        return values.get(address);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Memory memory = (Memory) o;
        return values.equals(memory.values);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values);
    }

    @Override
    public String toString() {
        return "Memory{" +
                "values=" + values +
                '}';
    }
}
