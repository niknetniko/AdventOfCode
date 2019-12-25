/**
 * @author Niko Strijbol
 */
public enum Command {
    INC {
        @Override
        public int apply(int original, int diff) {
            return original + diff;
        }
    }, DEC {
        @Override
        public int apply(int original, int diff) {
            return original - diff;
        }
    };

    public abstract int apply(int original, int diff);

    public static Command fromString(String string) {
        switch (string.trim()) {
            case "inc": return INC;
            case "dec": return DEC;
            default: throw new IllegalArgumentException();
        }
    }
}