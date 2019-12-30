package be.strijbol.advent.mmxix.codes;

/**
 * @author Niko Strijbol
 */
public abstract class Parameter {

    private Parameter() {}

    public abstract long reduce(Memory memory);

    public static Parameter read(long value, ParameterMode mode) {
        return new Parameter() {
            @Override
            public long reduce(Memory memory) {
                return mode.resolve(value, memory, true);
            }
        };
    }

    public static Parameter write(long value, ParameterMode mode) {
        return new Parameter() {
            @Override
            public long reduce(Memory memory) {
                return mode.resolve(value, memory, false);
            }
        };
    }
}
