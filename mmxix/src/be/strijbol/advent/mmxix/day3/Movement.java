package be.strijbol.advent.mmxix.day3;

import be.strijbol.advent.common.geometry.GridDirection;

import java.util.Objects;

/**
 * @author Niko Strijbol
 */
class Movement {

    private final int amount;
    private final GridDirection direction;

    public Movement(int amount, GridDirection direction) {
        this.amount = amount;
        this.direction = direction;
    }

    public static Movement from(String movement) {
        return new Movement(Integer.parseInt(movement.substring(1)), GridDirection.fromString(movement.substring(0, 1)));
    }

    public GridDirection getDirection() {
        return direction;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Movement" + direction.name() + amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return amount == movement.amount &&
                direction == movement.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, direction);
    }
}
