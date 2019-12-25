package be.strijbol.advent.common.collections;

import be.strijbol.advent.common.tuple.Pair;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Combines two things into all possible pairs.
 * @param <L>
 * @param <R>
 */
public class CombinationsIterator<L, R> implements Iterator<Pair<L, R>> {

    // Immutable fields
    private final List<L> one;
    private final List<R> two;

    // Mutable fields
    private int nextIndex = 0;

    public CombinationsIterator(final List<L> one, final List<R> two) {
        this.one = one;
        this.two = two;
    }

    @Override
    public boolean hasNext() {
        return nextIndex < one.size() * two.size();
    }

    @Override
    public Pair<L, R> next() {
        // Convert index to two 2d.
        int i1 = nextIndex % one.size();
        int i2 = Math.floorDiv(nextIndex, one.size());
        nextIndex++;

        try {
            return Pair.of(one.get(i1), two.get(i2));
        } catch (IndexOutOfBoundsException e) {
            throw new NoSuchElementException("No more elements exist.");
        }
    }
}
