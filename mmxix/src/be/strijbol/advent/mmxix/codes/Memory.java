package be.strijbol.advent.mmxix.codes;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        int opcode = this.values.get(ip);
        return switch (opcode) {
            case 99 -> new HaltInstruction();
            default -> new MathInstruction(opcode, values.get(ip + 1), values.get(ip + 2), values.get(ip + 3));
        };
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
