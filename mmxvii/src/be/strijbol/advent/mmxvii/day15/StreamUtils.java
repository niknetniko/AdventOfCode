package be.strijbol.advent.mmxvii.day15;

import java.util.Iterator;
import java.util.PrimitiveIterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Niko Strijbol
 */
class StreamUtils {

    @FunctionalInterface
    public interface BiLongPredicate {
        boolean test(long value1, long value2);
    }

    public static Stream<Boolean> equalsZip(PrimitiveIterator.OfLong a, PrimitiveIterator.OfLong b, BiLongPredicate predicate) {
        final Iterator<Boolean> iterator = new Iterator<>() {
            @Override
            public boolean hasNext() {
                return a.hasNext() && b.hasNext();
            }

            @Override
            public Boolean next() {
                return predicate.test(a.next(), b.next());
            }
        };

        // Create a new, better spliterator.
        Spliterator<Boolean> result = Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED | Spliterator.IMMUTABLE | Spliterator.NONNULL);
        return StreamSupport.stream(result, false);
    }
}