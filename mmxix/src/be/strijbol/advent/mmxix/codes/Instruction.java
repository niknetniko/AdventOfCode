package be.strijbol.advent.mmxix.codes;

/**
 * Interface for the different instructions.
 *
 * @author Niko Strijbol
 */
public interface Instruction {

    /**
     * Execute the opcode.
     */
    void execute(Memory memory) throws HaltException;

    int getLength();
}
