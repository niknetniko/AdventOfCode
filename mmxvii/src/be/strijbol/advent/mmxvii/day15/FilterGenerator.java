package be.strijbol.advent.mmxvii.day15;

import java.util.PrimitiveIterator;
import java.util.function.LongPredicate;

/**
 * Filter the output of an iterator. Filtering means only values that satisfy a predicate will pass through. When calling
 * next, the filter generator will keep querying the underlying iterator until a valid value is found.
 *
 * @author Niko Strijbol
 */
class FilterGenerator implements PrimitiveIterator.OfLong {

    private final PrimitiveIterator.OfLong wrapped;
    private final LongPredicate predicate;

    public FilterGenerator(OfLong wrapped, LongPredicate predicate) {
        this.wrapped = wrapped;
        this.predicate = predicate;
    }

    @Override
    public long nextLong() {
        long v = wrapped.nextLong();
        while (!predicate.test(v) && hasNext()) {
            v = wrapped.nextLong();
        }
        // Assume there is a v.
        return v;
    }

    @Override
    public boolean hasNext() {
        return wrapped.hasNext();
    }

    public PrimitiveIterator.OfLong filter(LongPredicate filter) {
        return new FilterGenerator(this, filter);
    }
}