package solution;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Niko Strijbol
 */
public class SharedBuffer {

    private Map<Long, BlockingQueue<Long>> buffers = new HashMap<>();

    private final Object lock = new Object();

    public void send(long program, long value) throws InterruptedException {
        BlockingQueue<Long> queue;
        synchronized (lock) {
            queue = buffers.computeIfAbsent(program, i -> new LinkedBlockingQueue<>());
        }
        queue.put(value);
    }

    public long read(long program) throws InterruptedException {
        BlockingQueue<Long> queue;
        synchronized (lock) {
            queue = buffers.computeIfAbsent(program, i -> new LinkedBlockingQueue<>());
        }
        return queue.poll(2, TimeUnit.SECONDS);
    }

    public void print(long program) {
        BlockingQueue<Long> queue;
        synchronized (lock) {
            queue = buffers.computeIfAbsent(program, i -> new LinkedBlockingQueue<>());
        }
        System.out.println(queue.size());
    }
}