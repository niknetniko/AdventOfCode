import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static int one() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Day 9/input/input.txt"));
        String line = reader.readLine();

        int currentGroupScore = 0;
        boolean wasPreviousExclamation = false;
        boolean inGarbage = false;

        int total = 0;


        for (char token : line.toCharArray()) {

            switch (token) {
                case '{':
                    if (!inGarbage) {
                        currentGroupScore++;
                    }
                    break;
                case '}':
                    if (!inGarbage) {
                        total += currentGroupScore;
                        currentGroupScore--;
                    }
                    break;
                case '<':
                    inGarbage = true;
                    break;
                case '>':
                    if (inGarbage && !wasPreviousExclamation) {
                        inGarbage = false;
                    }
            }

            wasPreviousExclamation = token == '!' && !wasPreviousExclamation;
        }

        return total;
    }

    private static int two() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("Day 9/input/input.txt"));
        String line = reader.readLine();

        boolean wasPreviousExclamation = false;
        boolean inGarbage = false;

        int total = 0;
        boolean leading = false;


        for (char token : line.toCharArray()) {
            switch (token) {
                case '<':
                    if (!inGarbage) {
                        inGarbage = true;
                        leading = true;
                    }
                    break;
                case '>':
                    if (inGarbage && !wasPreviousExclamation) {
                        inGarbage = false;
                    }
            }

            if (inGarbage && !leading && !wasPreviousExclamation && token != '!') {
                total++;
            }

            wasPreviousExclamation = token == '!' && !wasPreviousExclamation;
            leading = false;
        }

        return total;
    }

    public static void main(String[] args) throws IOException {
        //System.out.println("One is " + one());
        System.out.println("Two is " + two());
    }

}
