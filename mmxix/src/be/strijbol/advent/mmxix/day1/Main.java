package be.strijbol.advent.mmxix.day1;

import be.strijbol.advent.common.io.Inputs;

import java.io.FileNotFoundException;
import java.util.function.LongUnaryOperator;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static long fuel(long mass) {
        return Math.floorDiv(mass, 3) - 2;
    }

    private static long compoundFuel(long mass) {
        long totalFuel = 0;
        long added = fuel(mass);
        while (added >= 0) {
            totalFuel += added;
            added = fuel(added);
        }
        return totalFuel;
    }

    public static long allFuel(LongUnaryOperator calculator) throws FileNotFoundException {
        return Inputs.lines(2019, 1)
                .mapToLong(Long::valueOf)
                .map(calculator)
                .sum();
    }

    public static long one() throws FileNotFoundException {
        return allFuel(Main::fuel);
    }

    public static long two() throws FileNotFoundException {
        return allFuel(Main::compoundFuel);
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("One is " + one());
        System.out.println("Two is " + two());
    }
}
