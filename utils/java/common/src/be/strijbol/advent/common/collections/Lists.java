package be.strijbol.advent.common.collections;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Niko Strijbol
 */
public final class Lists {

    private Lists() {
        // No.
    }

    public static <E> Stream<List<E>> partition(List<E> list, int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length = " + length);
        }
        if (list.size() <= 0) {
            return Stream.empty();
        }
        int fullChunks = (list.size() - 1) / length;
        return IntStream.range(0, fullChunks + 1).mapToObj(n -> list.subList(n * length, n == fullChunks ? list.size() : (n + 1) * length));
    }

    public static <E> Supplier<E> asSupplier(List<E> elements) {
        return new Supplier<>() {
            private final Iterator<E> iterator = elements.iterator();

            @Override
            public E get() {
                return iterator.next();
            }
        };
    }
}
