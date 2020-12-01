package be.strijbol.advent.mmxvii.day18;

/**
 * @author Niko Strijbol
 */
public class Operand {

    private final String literal;

    public Operand(String literal) {
        this.literal = literal;
    }

    public long resolve(Register register) {
        if (literal.length() == 1) {
            char character = literal.charAt(0);
            if (Character.isDigit(character)) {
                return Character.getNumericValue(character);
            } else {
                return register.read(this);
            }
        } else {
            return Integer.parseInt(literal);
        }
    }

    @Override
    public String toString() {
        return literal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operand operand = (Operand) o;

        return literal.equals(operand.literal);
    }

    @Override
    public int hashCode() {
        return literal.hashCode();
    }

    public char asRegisterLiteral() {
        return literal.toCharArray()[0];
    }
}