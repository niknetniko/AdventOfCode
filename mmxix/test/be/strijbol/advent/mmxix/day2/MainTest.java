package be.strijbol.advent.mmxix.day2;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Niko Strijbol
 */
class MainTest {

    @Test
    void partOne() throws FileNotFoundException {
        int result = Main.one();
        assertEquals(5866663, result);
    }

    @Test
    void partTwo() throws FileNotFoundException {
        int result = Main.two();
        assertEquals(4259, result);
    }
}