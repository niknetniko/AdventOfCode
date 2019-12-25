import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static List<Layer> readData() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("Day 13/input/input.txt"));
        Map<Integer, Layer> actual = reader.lines()
                .map(s -> {
                    String[] parts = s.split(": ");
                    return new Layer(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                })
                .collect(Collectors.toMap(Layer::getNumber, Function.identity()));
        int biggest = actual.keySet().stream().mapToInt(i -> i).max().getAsInt();
        List<Layer> layers = new ArrayList<>(biggest + 1);
        for (int i = 0; i <= biggest; i++) {
            layers.add(actual.getOrDefault(i, Layer.empty(i)));
        }
        return layers;
    }

    private static int one() throws FileNotFoundException {
        List<Layer> layers = readData();

        int score = 0;
        for (int i = 0; i < layers.size(); i++) {
            Layer layer = layers.get(i);
            // We are caught!
            if (layer.getScannerPositionAt(i) == 0) {
                score += layer.getNumber() * layer.getScannerSize();
            }
        }

        return score;
    }

    private static int two() throws FileNotFoundException {
        List<Layer> layers = readData();

        int delay = 0;
        boolean stopped = false;
        while (!stopped) {
            stopped = true;
            for (int i = 0; i < layers.size(); i++) {
                Layer layer = layers.get(i);
                // We are caught!
                if (layer.getScannerPositionAt(i + delay) == 0) {
                    delay++; // Continue to next delay.
                    stopped = false;
                    break;
                }
            }
        }

        return delay;
    }

    public static void main(String[] args) throws FileNotFoundException {
        //System.out.println("One is " + one());
        System.out.println("Two is " + two());
    }
}
