package be.strijbol.advent.common.util;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author Niko Strijbol
 */
public class CollectingConsumer<E> implements Consumer<E> {

    private E element;
    private final boolean multiple;

    public CollectingConsumer(boolean multiple) {
        this.multiple = multiple;
    }


    @Override
    public void accept(E e) {
        if (!multiple && element != null) {
            throw new IllegalStateException("Cannot overwrite already collected values");
        }
        element = e;
    }

    public Optional<E> getElement() {
        return Optional.ofNullable(element);
    }
}
