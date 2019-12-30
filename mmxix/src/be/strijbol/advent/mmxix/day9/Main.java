package be.strijbol.advent.mmxix.day9;

import be.strijbol.advent.common.collections.Lists;
import be.strijbol.advent.common.io.Inputs;
import be.strijbol.advent.common.util.CollectingConsumer;
import be.strijbol.advent.mmxix.codes.Computer;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static void one() throws FileNotFoundException {
        var program = Inputs.line(2019, 9);
        var c = new Computer();
        c.loadProgram(program);
        Consumer<Long> printer = System.out::println;
        var in = Lists.asSupplier(List.of(1L));
        c.execute(in, printer);
    }

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Day one is ");
        one();
        //Computer.repl(Inputs.line(2019, 9, "test.txt"));
    }
}
