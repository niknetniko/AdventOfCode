package be.strijbol.advent.mmxvii.day18;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Niko Strijbol
 */
public class Register {

    private final long programId;

    private final Map<Character, Long> register;

    public Register() {
        this(0);
    }

    public Register(long programId) {
        this.programId = programId;
        this.register = new HashMap<>(Map.of('p', programId));
    }

    public void set(Operand name, long value) {
        register.put(name.asRegisterLiteral(), value);
    }

    public long read(Operand name) {
        return register.getOrDefault(name.asRegisterLiteral(), 0L);
    }

    public void clear() {
        register.clear();
    }

    @Override
    public String toString() {
        return register.toString();
    }

    public long getProgramId() {
        return programId;
    }

    public long getOtherId() {
        if (programId == 1) {
            return 0;
        } else {
            return 1;
        }
    }
}