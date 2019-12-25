import java.util.PrimitiveIterator;
import java.util.function.Predicate;

/**
 * @author Niko Strijbol
 */
public class Main {

    public static String padLeftZero(String s, int n) {
        return String.format("%1$" + n + "s", s).replace(' ', '0');
    }

    private static long one() {

        final long START_A = 618;
        final long START_B = 814;
        final long FACTOR_A = 16807;
        final long FACTOR_B = 48271;

        final long MAX_SIZE = 40_000_000;

        final PrimitiveIterator.OfLong generatorA = Generator.from(FACTOR_A, START_A);
        final PrimitiveIterator.OfLong generatorB = Generator.from(FACTOR_B, START_B);

        final long mask = (1 << 16) - 1;
        final StreamUtils.BiLongPredicate p = (l1, l2) -> (l1 & mask) == (l2 & mask);

        return StreamUtils.equalsZip(generatorA, generatorB, p)
                .limit(MAX_SIZE)
                .filter(Predicate.isEqual(true))
                .count();
    }

    private static long two() {

        final long START_A = 618;
        final long START_B = 814;
        final long FACTOR_A = 16807;
        final long FACTOR_B = 48271;

        final long MAX_SIZE = 5_000_000;

        final PrimitiveIterator.OfLong generatorA = Generator.from(FACTOR_A, START_A).filter(l -> l % 4 == 0);
        final PrimitiveIterator.OfLong generatorB = Generator.from(FACTOR_B, START_B).filter(l -> l % 8 == 0);

        final long mask = (1 << 16) - 1;
        final StreamUtils.BiLongPredicate p = (l1, l2) -> (l1 & mask) == (l2 & mask);

        return StreamUtils.equalsZip(generatorA, generatorB, p)
                .limit(MAX_SIZE)
                .filter(Predicate.isEqual(true))
                .count();
    }

    public static void main(String[] args) {
        //System.out.println("One is " + one());
        System.out.println("Two is " + two());
    }

}
