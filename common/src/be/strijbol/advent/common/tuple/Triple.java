/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package be.strijbol.advent.common.tuple;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * <p>A triple consisting of three elements.</p>
 *
 * <p>This class is an abstract implementation defining the basic API.
 * It refers to the elements as 'left', 'middle' and 'right'.</p>
 *
 * <p>Subclass implementations may be mutable or immutable.
 * However, there is no restriction on the type of the stored objects that may be stored.
 * If mutable objects are stored in the triple, then the triple itself effectively becomes mutable.</p>
 *
 * @param <L> the left element type
 * @param <M> the middle element type
 * @param <R> the right element type
 * @since 3.2
 */
@SuppressWarnings("unused")
public abstract class Triple<L, M, R> implements Serializable {

    private static final class TripleAdapter<L, M, R> extends Triple<L, M, R> {

        private static final long serialVersionUID = 1L;

        @Override
        public L getLeft() {
            return null;
        }

        @Override
        public M getMiddle() {
            return null;
        }

        @Override
        public R getRight() {
            return null;
        }

    }

    /**
     * Serialization version
     */
    private static final long serialVersionUID = 1L;

    /**
     * An empty array.
     * <p>
     * Consider using {@link #emptyArray()} to avoid generics warnings.
     * </p>
     *
     * @since 3.10.
     */
    public static final Triple<?, ?, ?>[] EMPTY_ARRAY = new TripleAdapter[0];

    /**
     * Returns the empty array singleton that can be assigned without compiler warning.
     *
     * @param <L> the left element type
     * @param <M> the middle element type
     * @param <R> the right element type
     * @return the empty array singleton that can be assigned without compiler warning.
     * @since 3.10.
     */
    @SuppressWarnings("unchecked")
    public static <L, M, R> Triple<L, M, R>[] emptyArray() {
        return (Triple<L, M, R>[]) EMPTY_ARRAY;
    }

    /**
     * <p>Obtains an immutable triple of three objects inferring the generic types.</p>
     *
     * <p>This factory allows the triple to be created using inference to
     * obtain the generic types.</p>
     *
     * @param <L>    the left element type
     * @param <M>    the middle element type
     * @param <R>    the right element type
     * @param left   the left element, may be null
     * @param middle the middle element, may be null
     * @param right  the right element, may be null
     * @return a triple formed from the three parameters, not null
     */
    public static <L, M, R> Triple<L, M, R> of(final L left, final M middle, final R right) {
        return new ImmutableTriple<>(left, middle, right);
    }

    //-----------------------------------------------------------------------

    /**
     * <p>Compares this triple to another based on the three elements.</p>
     *
     * @param obj the object to compare to, null returns false
     * @return true if the elements of the triple are equal
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof Triple<?, ?, ?>) {
            final Triple<?, ?, ?> other = (Triple<?, ?, ?>) obj;
            return Objects.equals(getLeft(), other.getLeft())
                    && Objects.equals(getMiddle(), other.getMiddle())
                    && Objects.equals(getRight(), other.getRight());
        }
        return false;
    }

    //-----------------------------------------------------------------------

    /**
     * <p>Gets the left element from this triple.</p>
     *
     * @return the left element, may be null
     */
    public abstract L getLeft();

    /**
     * <p>Gets the middle element from this triple.</p>
     *
     * @return the middle element, may be null
     */
    public abstract M getMiddle();

    /**
     * <p>Gets the right element from this triple.</p>
     *
     * @return the right element, may be null
     */
    public abstract R getRight();

    /**
     * <p>Returns a suitable hash code.</p>
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return (getLeft() == null ? 0 : getLeft().hashCode()) ^
                (getMiddle() == null ? 0 : getMiddle().hashCode()) ^
                (getRight() == null ? 0 : getRight().hashCode());
    }

    /**
     * <p>Returns a String representation of this triple using the format {@code ($left,$middle,$right)}.</p>
     *
     * @return a string describing this object, not null
     */
    @Override
    public String toString() {
        return "(" + getLeft() + "," + getMiddle() + "," + getRight() + ")";
    }

    /**
     * <p>Formats the receiver using the given format.</p>
     *
     * <p>This uses {@link java.util.Formattable} to perform the formatting. Three variables may
     * be used to embed the left and right elements. Use {@code %1$s} for the left
     * element, {@code %2$s} for the middle and {@code %3$s} for the right element.
     * The default format used by {@code toString()} is {@code (%1$s,%2$s,%3$s)}.</p>
     *
     * @param format the format string, optionally containing {@code %1$s}, {@code %2$s} and {@code %3$s}, not null
     * @return the formatted string, not null
     */
    public String toString(final String format) {
        return String.format(format, getLeft(), getMiddle(), getRight());
    }

    /**
     * Get a triple as a stream. Only works if all elements are the same type and not null.
     *
     * @param pair The pair to stream.
     * @param <L>  The type of the elements.
     * @return The stream containing the left and then the right element.
     */
    public static <L> Stream<L> stream(Triple<L, L, L> pair) {
        return StreamSupport.stream(iterable(pair).spliterator(), false);
    }

    /**
     * Create an iterable from the triple. Only works if all elements are the same type and not null.
     *
     * @param triple The triple to convert.
     * @param <L>    The type of the elements.
     * @return An iterable for the triple.
     */
    public static <L> Iterable<L> iterable(Triple<L, L, L> triple) {
        return () -> new Iterator<>() {
            private boolean givenLeft = false;
            private boolean givenMiddle = false;
            private boolean givenRight = false;

            @Override
            public boolean hasNext() {
                return !givenLeft || !givenRight || !givenMiddle;
            }

            @Override
            public L next() {
                if (!givenLeft) {
                    givenLeft = true;
                    return triple.getLeft();
                } else if (!givenMiddle) {
                    givenMiddle = true;
                    return triple.getMiddle();
                } else if (!givenRight) {
                    givenRight = true;
                    return triple.getRight();
                }
                throw new NoSuchElementException("No more elements in the triple.");
            }
        };
    }

    public static <T> Collector<T, List<T>, Triple<T, T, T>> asTriple() {
        return Collector.of(
                ArrayList::new,
                (ts, t) -> {
                    if (ts.size() >= 3) {
                        throw new IllegalStateException("A triple cannot have more than 3 elements.");
                    }
                    ts.add(t);
                },
                (ts, ts2) -> {
                    var a = new ArrayList<T>(ts.size() + ts2.size());
                    a.addAll(ts);
                    a.addAll(ts2);
                    return a;
                },
                ts -> {
                    if (ts.size() != 3) {
                        throw new IllegalStateException("A triple must have 3 types.");
                    }
                    return Triple.of(ts.get(0), ts.get(1), ts.get(2));
                }
        );
    }
}
