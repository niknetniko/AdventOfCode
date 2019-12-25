import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static int one() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("Day 10/input/input.txt"));
        List<Integer> lengths = Arrays.stream(reader.readLine().split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        CircularList<Integer> list = new CircularList<>(IntStream.rangeClosed(0, 255).boxed().collect(Collectors.toList()));

        int currentPosition = 0;
        int currentSkipSize = 0;
        for (int length : lengths) {
            Selection selection = new Selection(currentPosition, currentPosition + length);
            selection.reverse(list);
            currentPosition += length + currentSkipSize;
            currentSkipSize++;
        }

        return list.get(0) * list.get(1);
    }

    private static String two() throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("Day 10/input/input.txt"));
        List<Integer> lengths = Arrays.stream(reader.readLine().split(""))
                .map(String::trim)
                .flatMapToInt(String::chars)
                .boxed()
                .collect(Collectors.toList());

        KnotHash hash = new KnotHash(64);

        return hash.apply(lengths);
    }

    public static void main(String[] args) throws IOException {
        //System.out.println("One is " + one());
        System.out.println("Two is " + two());
    }

}
