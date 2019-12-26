package be.strijbol.advent.mmxix.codes;

/**
 * @author Niko Strijbol
 */
public class Parameter {

    private final int value;
    private final ParameterMode mode;

    public Parameter(int value, ParameterMode mode) {
        this.value = value;
        this.mode = mode;
    }

    public int reduce(Memory memory) {
        return mode.getValue(value, memory);
    }
}
