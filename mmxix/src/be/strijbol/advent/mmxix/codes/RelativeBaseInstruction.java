package be.strijbol.advent.mmxix.codes;

/**
 * @author Niko Strijbol
 */
class RelativeBaseInstruction implements Instruction {

    private final Parameter adjustment;

    RelativeBaseInstruction(Parameter adjustment) {
        this.adjustment = adjustment;
    }

    @Override
    public void execute(Memory memory) throws HaltException {
        memory.adjustReliveBase(Math.toIntExact(adjustment.reduce(memory)));
    }

    @Override
    public int getLength() {
        return 2;
    }
}
