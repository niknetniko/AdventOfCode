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

import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the Pair class.
 */
class PairTest {

    @Test
    public void testEmptyArrayLength() {
        @SuppressWarnings("unchecked") final Pair<Integer, String>[] empty = (Pair<Integer, String>[]) Pair.EMPTY_ARRAY;
        assertEquals(0, empty.length);
    }

    @Test
    public void testEmptyArrayGenerics() {
        final Pair<Integer, String>[] empty = Pair.emptyArray();
        assertEquals(0, empty.length);
    }

    @Test
    public void testFormattable_padded() {
        final Pair<String, String> pair = Pair.of("Key", "Value");
        assertEquals("         (Key,Value)", String.format("%1$20s", pair));
    }

    @Test
    public void testFormattable_simple() {
        final Pair<String, String> pair = Pair.of("Key", "Value");
        assertEquals("(Key,Value)", String.format("%1$s", pair));
    }

    @Test
    public void testMapEntry() {
        final Pair<Integer, String> pair = ImmutablePair.of(0, "foo");
        final HashMap<Integer, String> map = new HashMap<>();
        map.put(0, "foo");
        final Entry<Integer, String> entry = map.entrySet().iterator().next();
        assertEquals(pair, entry);
        assertEquals(pair.hashCode(), entry.hashCode());
    }

    @Test
    public void testPairOfMapEntry() {
        final HashMap<Integer, String> map = new HashMap<>();
        map.put(0, "foo");
        final Entry<Integer, String> entry = map.entrySet().iterator().next();
        final Pair<Integer, String> pair = Pair.of(entry);
        assertEquals(entry.getKey(), pair.getLeft());
        assertEquals(entry.getValue(), pair.getRight());
    }

    @Test
    public void testPairOfObjects() {
        final Pair<Integer, String> pair = Pair.of(0, "foo");
        assertTrue(pair instanceof ImmutablePair<?, ?>);
        assertEquals(0, ((ImmutablePair<Integer, String>) pair).left.intValue());
        assertEquals("foo", ((ImmutablePair<Integer, String>) pair).right);
        final Pair<Object, String> pair2 = Pair.of(null, "bar");
        assertTrue(pair2 instanceof ImmutablePair<?, ?>);
        assertNull(((ImmutablePair<Object, String>) pair2).left);
        assertEquals("bar", ((ImmutablePair<Object, String>) pair2).right);
        Pair<Void, Void> pair3 = Pair.of(null, null);
        assertNull(pair3.getLeft());
        assertNull(pair3.getRight());
    }

    @Test
    public void testToString() {
        final Pair<String, String> pair = Pair.of("Key", "Value");
        assertEquals("(Key,Value)", pair.toString());
    }

    @Test
    public void testToStringCustom() {
        final Calendar date = Calendar.getInstance();
        date.set(2011, Calendar.APRIL, 25);
        final Pair<String, Calendar> pair = Pair.of("DOB", date);
        assertEquals("Test created on " + "04-25-2011", pair.toString("Test created on %2$tm-%2$td-%2$tY"));
    }

    @Test
    public void testStream() {
        final Pair<String, String> pair = Pair.of("Key", "Value");
        String[] expected = new String[]{"Key", "Value"};
        String[] actual = Pair.stream(pair).toArray(String[]::new);
        assertArrayEquals(expected, actual);
    }

}