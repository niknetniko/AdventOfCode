package solution;

import solution.instructions.ASnd;
import solution.instructions.Instruction;
import solution.instructions.Rcv;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author Niko Strijbol
 */
public class Main {

    private static void one() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("Day 18/input/input.txt"));
        List<Instruction> instructions = reader.lines()
                .map(new AssemblyParser(false))
                .collect(Collectors.toList());

        DebuggableInterpreter interpreter = new DebuggableInterpreter();
        interpreter.setBeforeExecution((register, instruction) -> {
            System.out.println("Evaluating " + instruction);
            return true;
        });
        interpreter.setAfterExecution((register, instruction) -> {
            System.out.println(register);
            if (instruction instanceof Rcv) {
                long operand = register.read(((Rcv) instruction).getOperand());
                return operand == 0;
            }
            return true;
        });
        interpreter.accept(instructions);
    }

    private static void two() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader("Day 18/input/input.txt"));
        List<Instruction> instructions = reader.lines()
                .map(new AssemblyParser(true))
                .collect(Collectors.toList());

        AtomicInteger counter = new AtomicInteger();

        MultithreadedDebugger interpreter = new MultithreadedDebugger();
        interpreter.setBeforeExecution((register, instruction, buffer) -> {
            //if (register.getProgramId() == 1) {
                System.out.println("Evaluating on program " + register.getProgramId() + ": " + instruction);
            //}

            return true;
        });
        interpreter.setAfterExecution((register, instruction, buffer) -> {
            if (register.getProgramId() == 1 && instruction instanceof ASnd) {
                counter.incrementAndGet();
            }
            return true;
        });

        Thread.UncaughtExceptionHandler handler = (t, e) -> System.out.println("Program has deadlocked! Counter is " + counter);
        interpreter.execute(instructions, 0, handler);
        interpreter.execute(instructions, 1, handler);
    }

    public static void main(String[] args) throws FileNotFoundException {
        //one();
        two();
    }
}