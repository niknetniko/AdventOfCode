import java.util.function.Consumer;

/**
 * @author Niko Strijbol
 */
public class SpinLock implements Consumer<CircularIntBuffer> {

    private final int insertions;
    private final int stepSize;

    public SpinLock(int stepSize) {
        this(stepSize, 2017);
    }

    public SpinLock(int stepSize, int insertions) {
        this.insertions = insertions;
        this.stepSize = stepSize;
    }

    @Override
    public void accept(CircularIntBuffer integers) {
        for (int i = 1; i <= insertions; i++) {
            integers.step(stepSize);
            integers.add(i);
        }
    }
}