package be.strijbol.advent.mmxvii.day8;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static Register one() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("Day 8/input/input.txt"));

        // Parse data.
        List<Instruction> instructions = reader.lines()
                .map(s -> {
                    String[] parts = s.split(" ");
                    Conditional conditional = new Conditional(parts[4], Operation.fromString(parts[5]), Integer.parseInt(parts[6]));
                    return new Instruction(parts[0], Command.fromString(parts[1]), Integer.parseInt(parts[2]), conditional);
                })
                .collect(Collectors.toList());

        Register register = new Register();

        for (Instruction i: instructions) {
            i.conditional.apply(register, i);
        }

        return register;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Register register = one();
        System.out.println("One is " + register.largestEndValue());
        System.out.println("Two is " + register.largestAbsoluteValue());
    }
}
