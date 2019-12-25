import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Calculates the Knot Hash of a String. The output is a list of hexadecimal numbers. The input is a list of bytes.
 *
 * @author Niko Strijbol
 */
public class KnotHash implements Function<List<Integer>, String> {

    private final int ROUNDS;

    public KnotHash(int rounds) {
        ROUNDS = rounds;
    }

    public KnotHash() {
        this(1);
    }

    @Override
    public String apply(List<Integer> lengths) {
        lengths = new ArrayList<>(lengths);
        lengths.addAll(List.of(17, 31, 73, 47, 23));
        CircularList<Integer> list = new CircularList<>(IntStream.rangeClosed(0, 255).boxed().collect(Collectors.toList()));
        int currentPosition = 0;
        int currentSkipSize = 0;
        for (int i = 0; i < ROUNDS; i++) {
            for (int length : lengths) {
                Selection selection = new Selection(currentPosition, currentPosition + length);
                selection.reverse(list);
                currentPosition += length + currentSkipSize;
                currentSkipSize++;
            }
        }

        return list.partition(16)
                .map(integers -> integers.stream().reduce(0, (i, j) -> i ^ j))
                .map(Integer::toHexString)
                .map(s -> s.length() == 1 ? "0" + s : s)
                .collect(Collectors.joining());
    }
}