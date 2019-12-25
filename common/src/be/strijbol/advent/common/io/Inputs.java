package be.strijbol.advent.common.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Stream;

/**
 * @author Niko Strijbol
 */
public class Inputs {


    public static Stream<String> lines(int year, int day) throws FileNotFoundException {
        String roman = getRomanNumber(year);
        String path = String.format("%1$s/src/be/strijbol/advent/%1$s/day%2$d/input.txt", roman, day);
        return new BufferedReader(new FileReader(path)).lines();
    }

    /**
     * From https://stackoverflow.com/a/39411250/1831741.
     */
    private static String getRomanNumber(int number) {
        return String.join("", "I".repeat(number))
                .replace("IIIII", "V")
                .replace("IIII", "IV")
                .replace("VV", "X")
                .replace("VIV", "IX")
                .replace("XXXXX", "L")
                .replace("XXXX", "XL")
                .replace("LL", "C")
                .replace("LXL", "XC")
                .replace("CCCCC", "D")
                .replace("CCCC", "CD")
                .replace("DD", "M")
                .replace("DCD", "CM");
    }
}
