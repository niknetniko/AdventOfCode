package be.strijbol.advent.mmxix.codes;

/**
 * @author Niko Strijbol
 */
public interface ParameterMode {

    ParameterMode POSITION = new Mode(0) {
        @Override
        public long resolve(long value, Memory memory, boolean isRead) {
            if (isRead) {
                return memory.read(value);
            } else {
                return value;
            }
        }
    };
    ParameterMode IMMEDIATE = new Mode(1) {
        @Override
        public long resolve(long value, Memory memory, boolean isRead) {
            if (isRead) {
                return value;
            } else {
                throw new IllegalStateException("Write addresses cannot be in immediate mode.");
            }
        }
    };

    ParameterMode RELATIVE = new Mode(2) {
        @Override
        public long resolve(long value, Memory memory, boolean isRead) {
            if (isRead) {
                return memory.read(memory.getRelativeBase() + value);
            } else {
                return memory.getRelativeBase() + value;
            }
        }
    };

    /**
     * Resolve the parameter mode, depending on if the param is in read or write mode.
     * @param value The value of the parameter.
     * @param memory The memory.
     * @param isRead If the parameter is used as read or not.
     * @return The value or address, depending on the mode.
     */
    long resolve(long value, Memory memory, boolean isRead);

    static ParameterMode get(int mode) {
        return switch (mode) {
            case 0 -> POSITION;
            case 1 -> IMMEDIATE;
            case 2 -> RELATIVE;
            default -> throw new IllegalArgumentException("Unknown parameter mode " + mode);
        };
    }

    abstract class Mode implements ParameterMode {
        private final int mode;

        protected Mode(int mode) {
            this.mode = mode;
        }

        @Override
        public String toString() {
            return "Mode " + mode;
        }
    }
}
