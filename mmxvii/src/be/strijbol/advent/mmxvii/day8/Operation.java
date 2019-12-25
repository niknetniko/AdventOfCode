package be.strijbol.advent.mmxvii.day8;

/**
 * @author Niko Strijbol
 */
enum Operation {
    LESS {
        @Override
        boolean compare(int a, int b) {
            return a < b;
        }
    }, GREATER {
        @Override
        boolean compare(int a, int b) {
            return a > b;
        }
    }, EQUAL {
        @Override
        boolean compare(int a, int b) {
            return a == b;
        }
    }, LESS_EQUAL {
        @Override
        boolean compare(int a, int b) {
            return a <= b;
        }
    }, GREAT_EQUAL {
        @Override
        boolean compare(int a, int b) {
            return a >= b;
        }
    }, NOT_EQUAL {
        @Override
        boolean compare(int a, int b) {
            return a != b;
        }
    };

    abstract boolean compare(int a, int b);

    public static Operation fromString(String string) {
        switch (string.trim()) {
            case "<": return LESS;
            case "<=": return LESS_EQUAL;
            case "==": return EQUAL;
            case "!=": return NOT_EQUAL;
            case ">=": return GREAT_EQUAL;
            case ">": return GREATER;
            default: throw new IllegalArgumentException();
        }
    }
}