import java.util.HashMap;
import java.util.Map;

/**
 * @author Niko Strijbol
 */
public class Register {

    private final Map<String, Integer> map = new HashMap<>();
    private int largest = 0;

    public int getValue(String register) {
        return map.getOrDefault(register, 0);
    }

    public void setValue(String register, int value) {
        map.put(register, value);
        if (value > largest) {
            largest = value;
        }
    }

    public int largestEndValue() {
        return map.values().stream().max(Integer::compare).orElse(0);
    }

    public int largestAbsoluteValue() {
        return largest;
    }
}