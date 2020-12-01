package be.strijbol.advent.mmxix.day8;

import be.strijbol.advent.common.geometry.Coordinates;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.stream.Collectors;

/**
 * @author Niko Strijbol
 */
public class Layer {

    private final int[][] data;

    public Layer(int[][] data) {
        this.data = data;
    }

    public static Layer parse(String data, int width, int height) {
        // Split into
        int[][] array = new int[height][width];
        for (int i = 0; i < data.length(); i++) {
            var p = Coordinates.toTwoDimensions(i, width);
            array[(int) p.y()][(int) p.x()] = Character.getNumericValue(data.charAt(i));
        }
        return new Layer(array);
    }

    public static Layer of(int[][] data) {
        return new Layer(data);
    }

    public int count(int data) {
        return (int) Arrays.stream(this.data)
                .flatMapToInt(Arrays::stream)
                .filter(i -> i == data)
                .count();
    }

    public int at(int x, int y) {
        return data[x][y];
    }

    @Override
    public String toString() {
        String result = Arrays
                .stream(data)
                .map(Arrays::toString)
                .collect(Collectors.joining(System.lineSeparator()));
        return "Layer:\n" + result;
    }

    public String toString(IntFunction<String> mapper) {
        String result = Arrays
                .stream(data)
                .map(ints -> Arrays.stream(ints).mapToObj(mapper).collect(Collectors.joining()))
                .collect(Collectors.joining(System.lineSeparator()));
        return "Layer:\n" + result;
    }
}
