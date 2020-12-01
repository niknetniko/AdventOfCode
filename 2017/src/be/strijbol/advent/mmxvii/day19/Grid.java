package be.strijbol.advent.mmxvii.day19;

import java.util.Map;

/**
 * @author Niko Strijbol
 */
class Grid {

    private final char[][] actualGrid;

    Grid(char[][] actualGrid) {
        this.actualGrid = actualGrid;
    }

    int getX() {
        return actualGrid[0].length;
    }

    int getY() {
        return actualGrid.length;
    }

    char get(Map.Entry<Integer, Integer> c) {
        // Reverse the indices, meaning x is the horizontal axis.
        return actualGrid[c.getValue()][c.getKey()];
    }

    boolean isLetter(Map.Entry<Integer, Integer> c) {
        return Character.isLetter(get(c));
    }

    boolean isEmpty(Map.Entry<Integer, Integer> c) {
        return get(c) == ' ';
    }

    boolean isCrossRoad(Map.Entry<Integer, Integer> c) {
        return get(c) == '+';
    }

    boolean isPath(Map.Entry<Integer, Integer> c) {
        return isHorizontal(c) || isVertical(c) || isLetter(c) || isCrossRoad(c);
    }

    boolean isVertical(Map.Entry<Integer, Integer> c) {
        return get(c) == '|';
    }

    boolean isHorizontal(Map.Entry<Integer, Integer> c) {
        return get(c) == '-';
    }
}