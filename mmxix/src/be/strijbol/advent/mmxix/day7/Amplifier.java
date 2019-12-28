package be.strijbol.advent.mmxix.day7;

import be.strijbol.advent.common.collections.Lists;
import be.strijbol.advent.mmxix.codes.Computer;
import be.strijbol.advent.mmxix.codes.HaltException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Niko Strijbol
 */
class Amplifier implements Consumer<Integer> {

    private final String name;
    private final Computer computer;

    private Consumer<Integer> output;

    public Amplifier(String name, Computer computer) {
        this.name = name;
        this.computer = computer;
        if (!computer.hasProgram()) {
            throw new IllegalArgumentException("The computer needs a program.");
        }
    }

    public void setOutput(Consumer<Integer> output) {
        this.output = output;
    }

    @Override
    public void accept(Integer integer) {
        if (output == null) {
            throw new IllegalStateException("The amplifier needs to be hooked up with output.");
        }
        computer.execute(new Supplier<>() {
            final Supplier<Integer> nested = Lists.asSupplier(List.of(integer));
            @Override
            public Integer get() {
                try {
                    return nested.get();
                } catch (NoSuchElementException e) {
                    throw new HaltException();
                }
            }
        }, output);
    }

    @Override
    public String toString() {
        return "Amplifier{" + name + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amplifier amplifier = (Amplifier) o;
        return name.equals(amplifier.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public Consumer<Integer> getOutput() {
        return output;
    }
}
