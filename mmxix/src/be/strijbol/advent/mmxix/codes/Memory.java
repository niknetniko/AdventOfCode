package be.strijbol.advent.mmxix.codes;

import be.strijbol.advent.common.tuple.Pair;

import java.util.*;

/**
 * The memory of the computer.
 *
 * @author Niko Strijbol
 */
public class Memory {

    private final List<Long> values;
    private int relativeBase = 0;

    Memory(List<Long> values) {
        this.values = new ArrayList<>(values);
    }

    /**
     * Read the next instruction in memory at the given instruction pointer.
     *
     * @param ip Where to read the next instruction (the instruction pointer).
     * @return The instruction.
     */
    public Instruction readNext(int ip) {
        var rawCode = Math.toIntExact(this.values.get(ip));
        var opcode = rawCode % 100;
        // We assume max three operands.
        var mode1 = (rawCode / 100) % 10;
        var mode2 = (rawCode / 1_000) % 10;
        var mode3 = (rawCode / 10_000) % 10;
        return switch (opcode) {
            case 99 -> new HaltInstruction();
            case 9 -> new RelativeBaseInstruction(rp(ip, mode1, 1));
            case 8 -> new EqualsInstruction(rp(ip, mode1, 1), rp(ip, mode2, 2), wp(ip, mode3, 3));
            case 7 -> new LessThanInstruction(rp(ip, mode1, 1), rp(ip, mode2, 2), wp(ip, mode3, 3));
            case 6 -> new JumpIfFalseInstruction(rp(ip, mode1, 1), rp(ip, mode2, 2));
            case 5 -> new JumpIfTrueInstruction(rp(ip, mode1, 1), rp(ip, mode2, 2));
            case 4 -> new OutputInstruction(rp(ip, mode1, 1));
            case 3 -> new InputInstruction(wp(ip, mode1, 1));
            case 2 -> new MultiplyInstruction(rp(ip, mode1, 1), rp(ip, mode2, 2), wp(ip, mode3, 3));
            case 1 -> new AddInstruction(rp(ip, mode1, 1), rp(ip, mode2, 2), wp(ip, mode3, 3));
            default -> throw new IllegalArgumentException("Unknown opcode " + opcode);
        };
    }

    private Parameter rp(int ip, int mode, int number) {
        return Parameter.read(values.get(ip + number), ParameterMode.get(mode));
    }

    private Parameter wp(int ip, int mode, int number) {
        return Parameter.write(values.get(ip + number), ParameterMode.get(mode));
    }

    public long output() {
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

    void write(long address, long value) {
        if (address < 0) {
            throw new IndexOutOfBoundsException("No negative memory addresses allowed");
        }
        if (address >= this.values.size()) {
            this.values.addAll(Collections.nCopies((int) address - this.values.size() + 1, 0L));
        }
        this.values.set(Math.toIntExact(address), value);
    }

    long read(long address) {
        if (address < 0) {
            throw new IndexOutOfBoundsException("No negative memory addresses allowed");
        }
        if (address >= this.values.size()) {
            return 0;
        }
        return values.get(Math.toIntExact(address));
    }

    public int getRelativeBase() {
        return relativeBase;
    }

    public void adjustReliveBase(int adjustment) {
        this.relativeBase += adjustment;
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
