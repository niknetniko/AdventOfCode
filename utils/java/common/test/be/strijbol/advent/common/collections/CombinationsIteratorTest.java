package be.strijbol.advent.common.collections;

import be.strijbol.advent.common.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Niko Strijbol
 */
class CombinationsIteratorTest {

    @Test
    void basicTest() {
        var one = List.of(1, 2, 3);
        var two = List.of(3, 4, 5);

        var iterator = new CombinationsIterator<>(one, two);

        List<Pair<Integer, Integer>> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);


        assertEquals(9, list.size());
        assertEquals(Pair.of(1, 3), list.get(0));
        assertEquals(Pair.of(2, 3), list.get(1));
        assertEquals(Pair.of(3, 3), list.get(2));
        assertEquals(Pair.of(1, 4), list.get(3));
        assertEquals(Pair.of(2, 4), list.get(4));
        assertEquals(Pair.of(3, 4), list.get(5));
        assertEquals(Pair.of(1, 5), list.get(6));
        assertEquals(Pair.of(2, 5), list.get(7));
        assertEquals(Pair.of(3, 5), list.get(8));
    }
}