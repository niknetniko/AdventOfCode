package be.strijbol.advent.mmxix.day8;

import be.strijbol.advent.common.collections.Lists;
import be.strijbol.advent.common.io.Inputs;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static final int WIDTH = 25;
    private static final int HEIGHT = 6;

    public static List<Layer> parse() throws FileNotFoundException {
        String line = Inputs.line(2019, 8);
        var layers = new ArrayList<Layer>();
        for (int i = 0; i * (WIDTH * HEIGHT) < line.length(); i++) {
            var start = i * (WIDTH * HEIGHT);
            var end = (i + 1) * (WIDTH * HEIGHT);
            var layerString = line.substring(start, end);
            layers.add(Layer.parse(layerString, WIDTH, HEIGHT));
        }
        return layers;
    }

    public static int one() throws FileNotFoundException {
        var layers = parse();
        var zeroComp = Comparator.<Layer, Integer>comparing(layer -> layer.count(0));
        var zeroLayer = layers.stream().min(zeroComp).orElseThrow();
        var numberOf1 = zeroLayer.count(1);
        var numberOf2 = zeroLayer.count(2);
        return numberOf1 * numberOf2;
    }

    public static Layer two() throws FileNotFoundException {
        var layers = parse();
        System.out.println("Number of layers is " + layers.size());
        int[][] finalLayer = new int[HEIGHT][WIDTH];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                // Get the layered result, i.e. the first non-zero element.
                final var vi = i;
                final var vj = j;
                finalLayer[i][j] = layers.stream()
                        .mapToInt(layer -> layer.at(vi, vj))
                        .filter(k -> k != 2)
                        .findFirst()
                        .orElse(2);
            }
        }
        return Layer.of(finalLayer);
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Day 1 is " + one());
        System.out.println("Day 2 is " + two().toString(value -> switch (value) {
            case 0, 2 -> " ";
            case 1 -> "█";
            default -> throw new AssertionError("Not possible");
        }));
    }
}
