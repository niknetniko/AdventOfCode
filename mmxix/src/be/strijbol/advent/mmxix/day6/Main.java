package be.strijbol.advent.mmxix.day6;

import be.strijbol.advent.common.collections.ReverseList;
import be.strijbol.advent.common.io.Inputs;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static Map<String, Planet> objectMap() throws FileNotFoundException {
        var objectMap = new HashMap<String, Planet>();
        Inputs.lines(2019, 6).forEach(s -> {
            String[] elements = s.split("\\)");
            objectMap.computeIfAbsent(elements[1], Planet::new)
                    .setOrbits(objectMap.computeIfAbsent(elements[0], Planet::new));
        });
        return objectMap;
    }

    private static int one() throws FileNotFoundException {
        return objectMap().values().stream()
                .mapToInt(Planet::count)
                .sum();
    }

    private static Planet common(List<Planet> one, List<Planet> two) {
        var setTwo = new HashSet<>(two);
        for (var planet : one) {
            if (setTwo.contains(planet)) {
                return planet;
            }
        }
        throw new IllegalStateException("No common ancestor in paths");
    }

    public static int two() throws FileNotFoundException {
        var objectMap = objectMap();
        var me = objectMap.get("YOU");
        var santa = objectMap.get("SAN");

        var mePath = me.pathToComm().collect(ReverseList.toReverseList());
        var santaPath = santa.pathToComm().collect(ReverseList.toReverseList());

        // We now find the first common element in both paths.
        var common = common(mePath, santaPath);
        var meDistance = mePath.indexOf(common);
        var santaDistance = santaPath.indexOf(common);
        return meDistance + santaDistance - 2;
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Day 1 is " + one());
        System.out.println("Day 2 is " + two());
    }

}
