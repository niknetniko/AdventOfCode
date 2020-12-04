package be.strijbol.advent.common.util;

/**
 * <a href="http://mathworld.wolfram.com/Factorial.html">Factorial of a number</a>.
 * Taken from Apache Commons.
 */
public final class Factorial {

    /**
     * All long-representable factorials.
     */
    static final long[] FACTORIALS = new long[]{
            1L, 1L, 2L,
            6L, 24L, 120L,
            720L, 5040L, 40320L,
            362880L, 3628800L, 39916800L,
            479001600L, 6227020800L, 87178291200L,
            1307674368000L, 20922789888000L, 355687428096000L,
            6402373705728000L, 121645100408832000L, 2432902008176640000L
    };

    /**
     * Private constructor.
     */
    private Factorial() {
        // intentionally empty.
    }

    /**
     * Computes the factorial of {@code n}.
     *
     * @param n Argument.
     * @return {@code n!}
     * @throws IllegalArgumentException if {@code n < 0}.
     * @throws IllegalArgumentException if {@code n > 20} (the factorial
     *                                  value is too large to fit in a {@code long}).
     */
    public static long value(int n) {
        if (n < 0 || n > 20) {
            throw new IllegalArgumentException("Number is too big.");
        }

        return FACTORIALS[n];
    }
}