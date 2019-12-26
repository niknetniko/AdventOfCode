package be.strijbol.advent.mmxix.codes;

/**
 * @author Niko Strijbol
 */
public interface ParameterMode {

    int getValue(int value, Memory memory);

    static ParameterMode get(int mode) {
        return switch (mode) {
            // Position mode.
            case 0 -> (value, memory) -> memory.read(value);
            // Immediate mode.
            case 1 -> (value, memory) -> value;
            default -> throw new IllegalArgumentException("Unknown parameter mode " + mode);
        };
    }
}
