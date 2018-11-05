package com;

import org.junit.Test;
import prepare.util.Util;

import static org.junit.Assert.assertEquals;

/**
 * TODO: Fix the test case
 */
public class InterruptTest {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("MyThread: " + Thread.currentThread().getName() + " started");

            while(isInterrupted())
                Util.threadSleep(100);

            System.out.println("MyThread: " + Thread.currentThread().getName() + " completed");
        }
    }


    @Test
    public void testInterrupt() throws InterruptedException {
        final Thread thread = new MyThread();
        thread.start();
        thread.interrupt();
        thread.join(1000);

        assertEquals(thread.getState(), Thread.State.TERMINATED);
        // outdated version
//        thread.suspend();
//        thread.resume();
    }
}
