package be.strijbol.advent.mmxvii.day18;

/**
 * @author Niko Strijbol
 */
public class SoundCard {

    private long lastFrequency = 0;

    public void playSound(long frequency) {
        this.lastFrequency = frequency;
    }

    public long getLastFrequency() {
        return lastFrequency;
    }

}
