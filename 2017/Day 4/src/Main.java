import one.util.streamex.StreamEx;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static long one() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("Day 4/input/input.txt"));

        return StreamEx.of(reader.lines())
                .map(i -> i.split(" "))
                .map(strings -> {
                    long expected = 0;
                    HashSet<String> set = new HashSet<>();
                    for (String word : strings) {
                        Set<String> perm = generatePermutations(word);
                        expected += perm.size();
                        set.addAll(perm);
                    }
                    return set.size() == expected;
                })
                .filter(i -> i)
                .count();
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Result one is: " + one());
    }


    // From https://www.quickprogrammingtips.com/java/how-to-generate-all-permutations-of-a-string-in-java.html
    // Generate all permutations of a string in Java
    private static Set<String> generatePermutations(String input) {
        input = input.toLowerCase();
        Set<String> result = new HashSet<>();
        permutations("", input, result);
        return result;
    }

    // Recursive function for generating permutations
    // Each call contains the left side of a valid permutation and remaining characters
    private static void permutations(String prefix, String letters, Set<String> result) {
        if (letters.length() == 0) {
            result.add(prefix);
        } else {
            for (int i = 0; i < letters.length(); i++) {
                String letter = letters.substring(i, i + 1);
                String otherLetters = letters.substring(0, i) + letters.substring(i + 1);
                permutations(prefix + letter, otherLetters, result);
            }
        }
    }

}
