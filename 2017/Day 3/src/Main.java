import javafx.util.Pair;

/**
 * @author Niko Strijbol
 */
public class Main {

    public static long getSquare(long number) {

        // Find which square it is on.
        long current = 0;
        long n = 0;
        while (current < number) {
            current = (long) Math.pow(2*n + 1, 2);
            n++;
        }
        n--;

        Pair<Long, Long> startCoordinate = new Pair<>(n, -n + 1);

        long numbersOnSquare =  n* 8;

        Pair<Long, Long> coordinate = startCoordinate;
        long currentNumber = (long) Math.pow(2*(n - 1) + 1, 2) + 1;
        int passed = 0;
        while(currentNumber < number) {
            if (passed < (numbersOnSquare / 4)) {
                coordinate = new Pair<>(coordinate.getKey(), coordinate.getValue() + 1);
            } else if (passed < 2*(numbersOnSquare / 4)) {
                coordinate = new Pair<>(coordinate.getKey() - 1, coordinate.getValue());
            } else if (passed < 3*(numbersOnSquare / 4)) {
                coordinate = new Pair<>(coordinate.getKey(), coordinate.getValue() - 1);
            } else {
                coordinate = new Pair<>(coordinate.getKey() + 1, coordinate.getValue());
            }
            if (passed >= numbersOnSquare) {
                throw new IllegalStateException();
            }
            currentNumber++;
            passed++;
        }

        return Math.abs(coordinate.getKey()) + Math.abs(coordinate.getValue());

        // We have the number now. The we must find which position it

    }

    public static void main(String[] args) {
        System.out.println(getSquare(325489));
    }

}
