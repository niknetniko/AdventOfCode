package be.strijbol.advent.mmxvii.day16;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Niko Strijbol
 */
public class Main {

    // TODO: redo this

    public static void main(String[] args) throws IOException {
        //System.out.println("One is " + one());
        System.out.println("Two is " + two());
    }

    private static String one() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Day 16/input/input.txt"));
        String[] moves = reader.readLine().split(",");
        return dance("abcdefghijklmnop", moves);
    }

    private static String two() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Day 16/input/input.txt"));
        final String[] moves = reader.readLine().split(",");
        String current = "abcdefghijklmnop";

        // Find pattern.
        Set<String> occurred = new HashSet<>();
        occurred.add(current);
        long i;
        for (i = 0; i < 1_000_000_000; i++) {
            current = dance(current, moves);
            if (!occurred.add(current)) {
                System.out.println("Same at " + i);
                break;
            }
        }

        // Calculate how many we must actually do.
        long actual = 1_000_000_000 % i;
        System.out.println("Actual is " + actual);
        System.out.println("Value is " + current);
        for (long j = (1_000_000_000 / i) * i; j < 1_000_000_000; j++) {
            System.out.println("j is " + j);
            current = dance(current, moves);
        }

        return current;
    }

    private static void rotate(char[] nums, int k) {
        if (k > nums.length) {
            k = k % nums.length;
        }
        char[] result = new char[nums.length];
        System.arraycopy(nums, nums.length - k, result, 0, k);
        System.arraycopy(nums, 0, result, k, nums.length - k);
        System.arraycopy(result, 0, nums, 0, nums.length);
    }

    private static void swap(char[] nums, int i, int j) {
        char temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private static int indexOf(char needle, char[] haystack) {
        for (int i = 0; i < haystack.length; i++) {
            if (haystack[i] == needle) {
                return i;
            }
        }
        throw new IllegalArgumentException("Element not found.");
    }

    private static String dance(final String initial, final String[] moves) {

        char[] programs = initial.toCharArray();

        for (String move : moves) {

            switch (move.substring(0, 1)) {
                case "s":
                    int amount = Integer.parseInt(move.substring(1));
                    rotate(programs, amount);
                    break;
                case "x": {
                    String[] parts = move.substring(1).split("/");
                    swap(programs, Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                    break;
                }

                case "p": {
                    String[] parts = move.substring(1).split("/");
                    int i = indexOf(parts[0].charAt(0), programs);
                    int j = indexOf(parts[1].charAt(0), programs);
                    swap(programs, i, j);
                    break;
                }
            }
        }

        return new String(programs);
    }
}