package be.strijbol.advent.mmxvii.day11;

import be.strijbol.advent.common.geometry.WindDirection;
import be.strijbol.advent.common.tuple.Pair;

/**
 * @author Niko Strijbol
 */
class Neighbour extends Coordinate {

    private final Coordinate base;
    private final Pair<Long, Long> direction;

    public Neighbour(Coordinate base, WindDirection direction) {
        this.base = base;
        this.direction = direction.getOffset();
    }

    @Override
    public int getX() {
        return (int) (base.getX() + direction.getRight());
    }

    @Override
    public int getZ() {
        return (int) (base.getZ() + direction.getLeft());
    }
}