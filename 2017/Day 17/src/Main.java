/**
 * @author Niko Strijbol
 */
public class Main {

    private static int one() {
        CircularIntBuffer buffer = new CircularIntBuffer();
        SpinLock lock = new SpinLock(337);
        lock.accept(buffer);

        return buffer.getAfter(2017);
    }

    private static int two() {
        // This takes way too long, so we can't fully simulate it. Instead, we just track the value after 0.

//        CircularIntBuffer buffer = new CircularIntBuffer();
//        SpinLock lock = new SpinLock(337, 50_000_000);
//        lock.accept(buffer);
//
//        return buffer.getAfter(0);
        int currentPosition = 0;
        int value = 0;
        for (int i = 1; i <= 50_000_000; i++) {
            currentPosition = ((currentPosition + 337) % i) + 1;
            if (currentPosition == 1) {
                value = i;
            }
        }

        return value;
    }

    public static void main(String[] args) {
        System.out.println("One is " + one());
        System.out.println("Two is " + two());
    }

}
