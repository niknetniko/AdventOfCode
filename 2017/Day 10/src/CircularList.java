import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Niko Strijbol
 */
public class CircularList<E> extends AbstractList<E> {

    private final List<E> wrapping;

    public CircularList(List<E> wrapping) {
        this.wrapping = new ArrayList<>(wrapping);
    }

    public int index(int index) {
        int size = size();
        if (size == 0) {
            return index;
        } else {
            return index % size;
        }
    }

    @Override
    public E get(int index) {
        return wrapping.get(index(index));
    }

    @Override
    public int size() {
        return wrapping.size();
    }

    @Override
    public E set(int index, E element) {
        return wrapping.set(index(index), element);
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        int start = index(fromIndex);
        int end = index(toIndex);

        if (start < end) {
            return new ArrayList<>(wrapping.subList(start, end));
        } else {
            // We need to get [start,size()[ and [0, end[.
            List<E> result = new ArrayList<>(wrapping.subList(start, size()));
            result.addAll(wrapping.subList(0, end));
            return result;
        }
    }

    private static <T> Stream<List<T>> batches(List<T> source, int length) {
        if (length <= 0)
            throw new IllegalArgumentException("length = " + length);
        int size = source.size();
        if (size <= 0)
            return Stream.empty();
        int fullChunks = (size - 1) / length;
        return IntStream.range(0, fullChunks + 1).mapToObj(
                n -> source.subList(n * length, n == fullChunks ? size : (n + 1) * length));
    }

    public Stream<List<E>> partition(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("length = " + length);
        }
        if (size() <= 0) {
            return Stream.empty();
        }
        int fullChunks = (size() - 1) / length;
        return IntStream.range(0, fullChunks + 1).mapToObj(n -> wrapping.subList(n * length, n == fullChunks ? size() : (n + 1) * length));
    }
}