package be.strijbol.advent.mmxvii.day15;

import java.util.PrimitiveIterator;
import java.util.function.LongPredicate;

/**
 * @author Niko Strijbol
 */
class Generator implements PrimitiveIterator.OfLong {

    private static final long DIVIDER = 2147483647;

    private final long factor;
    private long previous;

    private Generator(long factor, long previous) {
        this.factor = factor;
        this.previous = previous;
    }

    public static Generator from(long factor, long start) {
        return new Generator(factor, start);
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public long nextLong() {
        previous = (previous * factor) % DIVIDER;
        return previous;
    }

    public PrimitiveIterator.OfLong filter(LongPredicate filter) {
        return new FilterGenerator(this, filter);
    }
}