/**
 * @author Niko Strijbol
 */
public class BaseCoordinate extends Coordinate {

    private final int x;
    private final int z;

    public BaseCoordinate(int x, int z) {
        this.x = x;
        this.z = z;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getZ() {
        return z;
    }
}
