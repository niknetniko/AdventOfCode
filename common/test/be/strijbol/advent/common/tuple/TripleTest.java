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

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test the Triple class.
 */
class TripleTest {

    @Test
    public void testEmptyArrayLength() {
        @SuppressWarnings("unchecked")
        final Triple<Integer, String, Boolean>[] empty = (Triple<Integer, String, Boolean>[]) Triple.EMPTY_ARRAY;
        assertEquals(0, empty.length);
    }

    @Test
    public void testEmptyArrayGenerics() {
        final Triple<Integer, String, Boolean>[] empty = Triple.emptyArray();
        assertEquals(0, empty.length);
    }

    @Test
    public void testFormattable_padded() {
        final Triple<String, String, String> triple = Triple.of("Key", "Something", "Value");
        assertEquals("         (Key,Something,Value)", String.format("%1$30s", triple));
    }

    @Test
    public void testFormattable_simple() {
        final Triple<String, String, String> triple = Triple.of("Key", "Something", "Value");
        assertEquals("(Key,Something,Value)", String.format("%1$s", triple));
    }

    @Test
    public void testToString() {
        final Triple<String, String, String> triple = Triple.of("Key", "Something", "Value");
        assertEquals("(Key,Something,Value)", triple.toString());
    }

    @Test
    public void testToStringCustom() {
        final Calendar date = Calendar.getInstance();
        date.set(2011, Calendar.APRIL, 25);
        final Triple<String, String, Calendar> triple = Triple.of("DOB", "string", date);
        assertEquals("Test created on " + "04-25-2011", triple.toString("Test created on %3$tm-%3$td-%3$tY"));
    }

    @Test
    public void testTripleOf() {
        final Triple<Integer, String, Boolean> triple = Triple.of(0, "foo", Boolean.TRUE);
        assertTrue(triple instanceof ImmutableTriple<?, ?, ?>);
        assertEquals(0, ((ImmutableTriple<Integer, String, Boolean>) triple).left.intValue());
        assertEquals("foo", ((ImmutableTriple<Integer, String, Boolean>) triple).middle);
        assertEquals(Boolean.TRUE, ((ImmutableTriple<Integer, String, Boolean>) triple).right);
        final Triple<Object, String, Long> triple2 = Triple.of(null, "bar", 200L);
        assertTrue(triple2 instanceof ImmutableTriple<?, ?, ?>);
        assertNull(((ImmutableTriple<Object, String, Long>) triple2).left);
        assertEquals("bar", ((ImmutableTriple<Object, String, Long>) triple2).middle);
        assertEquals(Long.valueOf(200L), ((ImmutableTriple<Object, String, Long>) triple2).right);
    }

    @Test
    public void testStream() {
        final Triple<String, String, String> triple = Triple.of("Left", "Middle", "Right");
        String[] expected = new String[]{"Left", "Middle", "Right"};
        String[] actual = Triple.stream(triple).toArray(String[]::new);
        assertArrayEquals(expected, actual);
    }
}
