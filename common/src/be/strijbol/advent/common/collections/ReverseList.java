/*
 * Copyright (C) 2007 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package be.strijbol.advent.common.collections;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static be.strijbol.advent.common.Preconditions.*;

/**
 * A view for a list that reverses the elements.
 *
 * Adapted from Guava.
 *
 * @author Niko Strijbol
 */
public class ReverseList<T> extends AbstractList<T> {
    private final List<T> forwardList;

    ReverseList(List<T> forwardList) {
        this.forwardList = Objects.requireNonNull(forwardList);
    }

    private int reverseIndex(int index) {
        int size = size();
        checkElementIndex(index, size);
        return (size - 1) - index;
    }

    private int reversePosition(int index) {
        int size = size();
        checkPositionIndex(index, size);
        return size - index;
    }

    @Override
    public void add(int index, @Nullable T element) {
        forwardList.add(reversePosition(index), element);
    }

    @Override
    public void clear() {
        forwardList.clear();
    }

    @Override
    public T remove(int index) {
        return forwardList.remove(reverseIndex(index));
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        subList(fromIndex, toIndex).clear();
    }

    @Override
    public T set(int index, @Nullable T element) {
        return forwardList.set(reverseIndex(index), element);
    }

    @Override
    public T get(int index) {
        return forwardList.get(reverseIndex(index));
    }

    @Override
    public int size() {
        return forwardList.size();
    }

    @NotNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        checkPositionIndexes(fromIndex, toIndex, size());
        return new ReverseList<>(forwardList.subList(reversePosition(toIndex), reversePosition(fromIndex)));
    }

    public static <T> Collector<T, ?, List<T>> toReverseList() {
        return Collectors.collectingAndThen(Collectors.toList(), ReverseList::new);
    }
}

