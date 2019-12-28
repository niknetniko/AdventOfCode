package be.strijbol.advent.mmxix.codes;

/**
 * Halt instruction; halts the program execution.
 */
class HaltInstruction implements Instruction {

    public HaltInstruction() {
    }

    @Override
    public void execute(Memory memory) throws HaltException {
        throw new HaltException();
    }

    @Override
    public int getLength() {
        return 1;
    }
}
