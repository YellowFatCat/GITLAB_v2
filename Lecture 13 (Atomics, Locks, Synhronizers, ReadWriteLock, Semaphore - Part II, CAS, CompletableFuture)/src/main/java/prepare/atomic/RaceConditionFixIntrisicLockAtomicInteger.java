package prepare.atomic;

import prepare.util.Util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;


public class RaceConditionFixIntrisicLockAtomicInteger {
    static class Counter {
        int counter = 0;

        synchronized void inc() {
            counter++;
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(2);

        // closure
        Counter counter = new Counter();

        final long COUNTER = 10_000L;

        // thread 1
        service.submit(() ->
                {
                    LongStream.range(0, COUNTER).forEach(i -> {
                        counter.inc();
                    });
                }
        );
        // thread 2
        service.submit(() ->
                {
                    LongStream.range(0, COUNTER).forEach(i -> {
                        counter.inc();
                    });
                }
        );

        Util.threadSleep(1100);
        // reject new tasks
        service.shutdown();
        // exit right after the tasks are completed
        service.awaitTermination(60, TimeUnit.SECONDS);

        if (counter.counter != 2 * COUNTER)
            System.err.println("Race Condition occured! Expected = " + (2 * COUNTER) + " Received = " + counter.counter);
        System.out.println("Counter = " + counter.counter);
    }
}
