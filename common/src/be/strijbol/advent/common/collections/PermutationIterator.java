package be.strijbol.advent.common.collections;

import be.strijbol.advent.common.util.Factorial;

import java.util.*;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;

/**
 * An iterator that will generate all permutations of a collection of elements.
 *
 * Algorithm from
 *   Donald L. Kreher en Douglas R. Stinson, “Combinatorial Algorithms: Generation, Enumeration and Search”,
 *   CRC Press, 1999 (chapter 2, section 2.4.1)
 *
 * @author Niko Strijbol
 */
public class PermutationIterator<E> implements Iterator<List<E>> {

    private final List<E> elements;
    private final int totalElements;
    private int nextRank = 0;

    public PermutationIterator(List<E> elements) {
        this.elements = elements;
        this.totalElements = (int) Factorial.value(elements.size());
    }

    private List<E> unrank(int rank) {
        var n = elements.size();
        var p = new int[n+1];
        Arrays.fill(p, 1);
        for (int i = 1; i < n; i++) {
            var d = (int) Math.floorDiv(rank % (Factorial.value(i + 1)),  Factorial.value(i));
            rank -= d * Factorial.value(i);
            p[n - i] = d + 1;
            for (int j = n - i + 1; j <= n; j++) {
                if (p[j] > d) {
                    p[j] += 1;
                }
            }
        }
        return Arrays.stream(p)
                .skip(1)
                .mapToObj(i -> elements.get(i - 1))
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasNext() {
        return nextRank < totalElements;
    }

    @Override
    public List<E> next() {
        return unrank(nextRank++);
    }

    public static <E> Spliterator<List<E>> spliterator(List<E> elements) {
        var iterator = new PermutationIterator<>(elements);
        return Spliterators.spliterator(iterator, iterator.totalElements,
                Spliterator.ORDERED | Spliterator.DISTINCT | Spliterator.SIZED | Spliterator.NONNULL);
    }
}
