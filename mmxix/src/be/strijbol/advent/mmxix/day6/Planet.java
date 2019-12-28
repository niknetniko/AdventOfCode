package be.strijbol.advent.mmxix.day6;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * A planet orbits other planets.
 *
 * @author Niko Strijbol
 */
class Planet {

    private final String name;
    private Planet orbits;


    public Planet(String name) {
        this.name = name;
    }

    public void setOrbits(Planet planet) {
        if (orbits != null) {
            throw new IllegalStateException("You cannot orbit two things.");
        }
        this.orbits = planet;
    }


    public int count() {
        if (orbits == null) {
            return 0;
        } else {
            return orbits.count() + 1;
        }
    }

    public Stream<Planet> pathToComm() {
        if (this.orbits == null) {
            assert this.name.equals("COM");
            return Stream.empty();
        }
        return Stream.concat(this.orbits.pathToComm(), Stream.of(this));
    }

    @Override
    public String toString() {
        return "Planet{" + name + ", orbits=" + orbits + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return name.equals(planet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
