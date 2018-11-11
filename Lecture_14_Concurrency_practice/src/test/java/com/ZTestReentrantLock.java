package com;

import org.junit.Test;

import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class ZTestReentrantLock {

    private static int counter = 0;

    private static ReentrantLock lock = new ReentrantLock();

    static void inc() {
        // TODO: add ReentrantLock
        try {
            counter++;
        } finally {
        }
    }

    @Test
    public void testThreadState() throws InterruptedException {
        Thread thread1 = createThread(() -> {
            IntStream.range(0, 10000).forEach((i) -> inc());
        });

        Thread thread2 = createThread(() -> {
            IntStream.range(0, 10000).forEach((i) -> inc());
        });

        assertEquals(thread1.getState(), Thread.State.NEW);
        assertEquals(thread2.getState(), Thread.State.NEW);

        //TODO: make runnable
        //TODO: make runnable
        // TODO: Insert start of threada
        assertEquals(thread1.getState(), Thread.State.RUNNABLE);
        assertEquals(thread2.getState(), Thread.State.RUNNABLE);

        // Add delay if necessary
        Thread.currentThread().sleep(100);

        // threads should run task to be put on hold
        assertEquals(counter, 20000);
    }

    private Thread createThread() {
        final Thread thread = new Thread();
        return thread;
    }

    private Thread createThread(Runnable runnable) {
        final Thread thread = new Thread(runnable);
        return thread;
    }

}
