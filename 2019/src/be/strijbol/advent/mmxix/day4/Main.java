package be.strijbol.advent.mmxix.day4;

import one.util.streamex.IntStreamEx;

import java.util.Arrays;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static IntStream range() {
        return IntStream.range(193651, 649730);
    }

    private static final BiPredicate<Integer, Integer> sameMatcher = Integer::equals;

    private static final Predicate<String> rule3 = s -> IntStreamEx.of(s.chars()).boxed().zipWith(s.chars().skip(1))
            .anyMatch(sameMatcher);

    private static BiPredicate<Integer, Integer> moreSameMatcher(String s) {
        return sameMatcher.and((i, a) -> s.chars().filter(c -> c == i).count() == 2);
    }

    private static final Predicate<String> rule32 = s -> IntStreamEx.of(s.chars()).boxed().zipWith(s.chars().skip(1))
            .anyMatch(moreSameMatcher(s));

    private static final Predicate<String> rule4 = s -> {
        var sorted = s.toCharArray();
        Arrays.sort(sorted);
        return Arrays.equals(s.toCharArray(), sorted);
    };

    private static long one() {
        return range()
                .mapToObj(String::valueOf)
                .filter(rule3)
                .filter(rule4)
                .count();
    }

    private static long two() {
        return range()
                .mapToObj(String::valueOf)
                .filter(rule32)
                .filter(rule4)
                .count();
    }

    public static void main(String[] args) {
        System.out.println("One is " + one());
        System.out.println("One is " + two());
    }
}