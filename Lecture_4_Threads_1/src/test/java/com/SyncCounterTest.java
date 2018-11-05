package com;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static prepare.util.Util.threadSleep;

public class SyncCounterTest {

    public static class CounterThread implements Runnable {

        private final String name;
        private final Counter counter;
        private final int total;

        public CounterThread(final String name, final Counter counter, int total) {
            this.name = name;
            this.counter = counter;
            this.total = total;
        }

        @Override
        public void run() {
            Thread.currentThread().setName(name);
            int i = 0;
            while (i < total) {
                i++;

                counter.inc();
                System.out.println(name + "; counter = " + counter.getCounter());

                threadSleep(5);
            }
        }
    }


    public static class Counter {

        private Integer counter;

        public Counter(Integer counter) {
            this.counter = counter;
        }

        public synchronized void inc() {
            counter++;
        }

        public Integer getCounter(){
            return counter;
        }
    }


    /**
     * TODO: Fix the test and the code to make it Thread-Safe
     *
     * @throws InterruptedException
     */
    @Test
    public void testSync() throws InterruptedException {

        final int total = 200;
        Counter counter = new Counter(0);
        Thread thread1 = new Thread(new CounterThread("Thread - 1", counter, total));
        Thread thread2 = new Thread(new CounterThread("Thread - 2", counter, total));

        thread1.start();
        thread2.start();

        thread2.join();

        assertEquals(2 * total, counter.getCounter().longValue());
    }
}
