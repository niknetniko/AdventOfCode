package be.strijbol.advent.mmxvii.day18;


import be.strijbol.advent.mmxvii.day18.instructions.*;

import java.util.Arrays;
import java.util.function.Function;

/**
 * @author Niko Strijbol
 */
public class AssemblyParser implements Function<String, Instruction> {

    private final boolean useCorrectAssembly;

    public AssemblyParser(boolean useCorrectAssembly) {
        this.useCorrectAssembly = useCorrectAssembly;
    }

    @Override
    public Instruction apply(String s) {
        Operand[] operands = Arrays.stream(s.substring(4).split(" "))
                .map(Operand::new)
                .toArray(Operand[]::new);
        switch (s.substring(0, 3)) {
            case "snd":
                if (useCorrectAssembly) {
                    return new ASnd(operands[0]);
                } else {
                    return new Snd(operands[0]);
                }
            case "set":
                return new Set(operands[0], operands[1]);
            case "add":
                return new Add(operands[0], operands[1]);
            case "mul":
                return new Mul(operands[0], operands[1]);
            case "mod":
                return new Mod(operands[0], operands[1]);
            case "rcv":
                if (useCorrectAssembly) {
                    return new ARcv(operands[0]);
                } else {
                    return new Rcv(operands[0]);
                }
            case "jgz":
                return new Jgz(operands[0], operands[1]);
            default:
                throw new IllegalArgumentException("Unknown instruction encountered.");
        }
    }
}
