package be.strijbol.advent.mmxvii.day20;


import be.strijbol.advent.common.geometry.Coordinate3D;
import be.strijbol.advent.common.tuple.Triple;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Niko Strijbol
 */
class Point {

    private final int id;
    private final Coordinate3D position;
    private final Triple<Long, Long, Long> velocity;
    private final Triple<Long, Long, Long> acceleration;

    private Point(int id, Coordinate3D position, Triple<Long, Long, Long> velocity, Triple<Long, Long, Long> acceleration) {
        this.id = id;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public static Point parse(int id, String string) {
        List<Triple<Long, Long, Long>> triples = Arrays.stream(string.split(", "))
                .map(s -> s.substring(2))
                .map(s -> Arrays.stream(s.split(" "))
                        .mapToLong(Long::valueOf)
                        .boxed()
                        .collect(Triple.asTriple()))
                .collect(Collectors.toList());
        assert triples.size() == 3;
        return new Point(id,
                Coordinate3D.of(triples.get(0)),
                triples.get(1),
                triples.get(2)
        );
    }

    Point tick() {
        Triple<Long, Long, Long> newVelocity = Triple.of(
                velocity.getLeft() + acceleration.getLeft(),
                velocity.getMiddle() + acceleration.getMiddle(),
                velocity.getRight() + acceleration.getRight()
        );
        Coordinate3D newPosition = position.plus(newVelocity);
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

    public Coordinate3D getPosition() {
        return position;
    }

    public Triple<Long, Long, Long> getVelocity() {
        return velocity;
    }

    public Triple<Long, Long, Long> getAcceleration() {
        return acceleration;
    }
}
