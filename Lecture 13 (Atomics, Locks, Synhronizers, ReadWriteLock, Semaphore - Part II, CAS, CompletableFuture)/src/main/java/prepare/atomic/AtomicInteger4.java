package prepare.atomic;

import com.sun.jndi.toolkit.ctx.AtomicContext;
import prepare.util.Util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;


/**
 * CAS Algorithm to increment with any value (demo)
 * CAS in implemented internally in concurrent library - no need to design a new algorithm
 * Test CAS reproduction
 */
public class AtomicInteger4 {
    static class Counter {
        AtomicLong counter = new AtomicLong(0);


        void inc(long addValue) {
            long initValue = counter.get();
            long newValue = addValue + initValue;
            while (!counter.compareAndSet(initValue, newValue)) {
                initValue = counter.get();
                newValue = addValue + initValue;
            }
        }

        long get() {
            return counter.get();
        }
    }

    static class SyncCounter {
        //        AtomicInteger counter = new AtomicInteger(0);
        long counter = 0;
        Object o = new Object();

        void inc(int newValue) {
            synchronized(o) {
                counter = counter + newValue;
            }
        }

        long get() {
            return counter;
        }
    }


    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(2);

        // closure
        Counter counter = new Counter();
        SyncCounter syncCounter = new SyncCounter();

        final long COUNTER = 1_000_000L;

        long start = System.currentTimeMillis();
        // thread 1
        service.submit(() ->
                {
                    LongStream.range(0, COUNTER).forEach(i -> {
                        counter.inc(10);
                    });
                }
        );
        // thread 2
        service.submit(() ->
                {
                    LongStream.range(0, COUNTER).forEach(i -> {
                        counter.inc(1);
                    });
                }
        ).get(); // wait
        long end = System.currentTimeMillis();
        System.out.println("Atomic CAS Execution time = " + (end - start));

        // ######################### Sync intricic Example #################
        start = System.currentTimeMillis();
        // thread 1
        service.submit(() ->
                {
                    LongStream.range(0, COUNTER).forEach(i -> {
                        syncCounter.inc(10);
                    });
                }
        );
        // thread 2
        service.submit(() ->
                {
                    LongStream.range(0, COUNTER).forEach(i -> {
                        syncCounter.inc(1);
                    });
                }
        ).get(); // wait
        end = System.currentTimeMillis();
        System.out.println("Sync Example Execution time = " + (end - start));

        // ##########################

        Util.threadSleep(2000);
        // reject new tasks
        service.shutdown();
        // exit right after the tasks are completed
        service.awaitTermination(60, TimeUnit.SECONDS);

        long expected = 1 * COUNTER + 10 * COUNTER;
        if (counter.get() != expected)
            System.err.println("Race Condition occurred! Expected = " + (expected) + " Received = " + counter.counter.get());
        System.out.println("Counter = " + counter.counter);
    }
}
