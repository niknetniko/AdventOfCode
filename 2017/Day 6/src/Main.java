import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static int one() throws IOException {

        // Read steps as integers.
        BufferedReader reader = new BufferedReader(new FileReader("Day 6/input/input.txt"));
        List<Integer> banks = Arrays.stream(reader.readLine().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        Set<List<Integer>> configurations = new HashSet<>();
        boolean hasFound = false;
        int counter = 0;
        while(!hasFound) {

            // Find highest
            int blocks = 0;
            int bank = 0;
            for (int i = 0; i < banks.size(); i++) {
                if (banks.get(i) > blocks) {
                    blocks = banks.get(i);
                    bank = i;
                }
            }

            // Clear highest.
            banks.set(bank, 0);

            // Begin distributing
            while(blocks != 0) {
                bank = (bank + 1) % banks.size();
                banks.set(bank, banks.get(bank) + 1);
                blocks--;
            }

            List<Integer> snapshot = new ArrayList<>(banks);
            if (!configurations.add(snapshot)) {
                hasFound = true;
            }
            counter++;
        }

        return counter;
    }

    private static int two() throws IOException {

        // Read steps as integers.
        BufferedReader reader = new BufferedReader(new FileReader("Day 6/input/input.txt"));
        List<Integer> banks = Arrays.stream(reader.readLine().split("\\s+"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        Set<List<Integer>> configurations = new HashSet<>();
        Map<List<Integer>, Integer> addedCounter = new HashMap<>();
        List<Integer> snapshot = null;
        boolean hasFound = false;
        int counter = 0;
        while(!hasFound) {

            // Find highest
            int blocks = 0;
            int bank = 0;
            for (int i = 0; i < banks.size(); i++) {
                if (banks.get(i) > blocks) {
                    blocks = banks.get(i);
                    bank = i;
                }
            }

            // Clear highest.
            banks.set(bank, 0);

            // Begin distributing
            while(blocks != 0) {
                bank = (bank + 1) % banks.size();
                banks.set(bank, banks.get(bank) + 1);
                blocks--;
            }

            snapshot = new ArrayList<>(banks);
            counter++;
            if (!configurations.add(snapshot)) {
                hasFound = true;
            } else {
                addedCounter.put(snapshot, counter);
            }

        }

        return counter - addedCounter.get(snapshot);
    }

    public static void main(String[] args) throws IOException {
       // System.out.println("One is " + one());
        System.out.println("Two is " + two());
    }

}
