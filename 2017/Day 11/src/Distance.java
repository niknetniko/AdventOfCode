/**
 * @author Niko Strijbol
 */
public class Distance {

    private final Coordinate one;
    private final Coordinate two;

    public Distance(Coordinate one, Coordinate two) {
        this.one = one;
        this.two = two;
    }

    public int asValue() {
        return (Math.abs(one.getX() - two.getX()) + Math.abs(one.getY() - two.getY()) + Math.abs(one.getZ() - two.getZ())) / 2;
    }
}