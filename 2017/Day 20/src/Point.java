import be.almiro.java.advent.LongTriple;
import be.almiro.java.advent.coordinate.Coordinate;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Niko Strijbol
 */
class Point {

    private final int id;
    private final Coordinate position;
    private final LongTriple velocity;
    private final LongTriple acceleration;

    private Point(int id, Coordinate position, LongTriple velocity, LongTriple acceleration) {
        this.id = id;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public static Point parse(int id, String string) {
        List<LongTriple> triples = Arrays.stream(string.split(", "))
                .map(s -> s.substring(2))
                .map(LongTriple::parse)
                .collect(Collectors.toList());
        assert triples.size() == 3;
        return new Point(id,
                new Coordinate(triples.get(0)),
                triples.get(1),
                triples.get(2)
        );
    }

    Point tick() {
        LongTriple newVelocity = velocity.plus(acceleration);
        Coordinate newPosition = position.plus(newVelocity);
        //System.out.println("Moving point " + id + " from " + position.toString() + " to " + newPosition);
        return new Point(id, newPosition, newVelocity, acceleration);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return this.id == point.id &&
                Objects.equals(position, point.position) &&
                Objects.equals(velocity, point.velocity) &&
                Objects.equals(acceleration, point.acceleration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, position, velocity, acceleration);
    }

    public int getId() {
        return id;
    }

    public Coordinate getPosition() {
        return position;
    }

    public LongTriple getVelocity() {
        return velocity;
    }

    public LongTriple getAcceleration() {
        return acceleration;
    }
}
