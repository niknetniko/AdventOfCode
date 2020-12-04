package be.strijbol.advent.common.collections;

import java.util.Collections;
import java.util.List;

/**
 * Denotes a selection (or range of elements) in a list.
 *
 * @author Niko Strijbol
 */
public class Selection {

    private final int startInclusive;
    private final int stopExclusive;

    public Selection(int startInclusive, int stopExclusive) {
        this.startInclusive = startInclusive;
        this.stopExclusive = stopExclusive;
    }

    /**
     * Reverse the sublist denoted by this selection.
     *
     * @param list The list to apply the reverse to.
     *
     * @param <E> The elements.
     */
    public <E> void reverse(List<E> list) {
        List<E> sublist = list.subList(startInclusive, stopExclusive);
        Collections.reverse(sublist);
        // Re-assign the variables.
        for (int i = 0; i < (stopExclusive - startInclusive); i++) {
            list.set(startInclusive + i, sublist.get(i));
        }
    }
}
