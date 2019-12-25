import java.util.*;

/**
 * @author Niko Strijbol
 */
public class CircularIntBuffer implements Collection<Integer> {

    private final List<Integer> underlyingData = new LinkedList<>(List.of(0));
    private int currentPosition = 0;

    /**
     * Step forward a number of times. This will move the current position.
     *
     * @param forward The number of times to step forward.
     */
    public void step(int forward) {
        currentPosition = (currentPosition + forward) % size();
    }

    public Integer getAfter(int after) {
        return underlyingData.get(after);
    }

    @Override
    public int size() {
        return underlyingData.size();
    }

    @Override
    public boolean isEmpty() {
        return underlyingData.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return underlyingData.contains(o);
    }

    @Override
    public Iterator<Integer> iterator() {
        return underlyingData.iterator();
    }

    @Override
    public Object[] toArray() {
        return underlyingData.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return underlyingData.toArray(a);
    }

    @Override
    public boolean add(Integer integer) {
        underlyingData.add(currentPosition + 1, integer);
        currentPosition += 1;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return underlyingData.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Integer> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        underlyingData.clear();
        underlyingData.add(0);
        currentPosition = 0;
    }
}
