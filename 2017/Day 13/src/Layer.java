/**
 * @author Niko Strijbol
 */
public class Layer {

    private final int number;
    private final int scannerSize;

    public Layer(int number, int scannerSize) {
        this.number = number;
        this.scannerSize = scannerSize;
    }

    /**
     * Get the position just before the scanner moves in the picoseconds.
     *
     * For example, if the scanner starts at 0, picosecond will return 0 (and then it moves).
     */
    public int getScannerPositionAt(int picoseconds) {
        if (scannerSize == -1) {
            return scannerSize;
        } else {

            // Should be [0, scannerSize[.
            int current = picoseconds % ((scannerSize - 1) * 2);

            if (current < scannerSize) {
                return current;
            } else {
                return (scannerSize - 1) - (scannerSize - current);
            }
        }
    }

    public int getNumber() {
        return number;
    }

    public int getScannerSize() {
        return scannerSize;
    }

    public static Layer empty(int number) {
        return new Layer(number, -1);
    }
}
