package com;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ZInfiniteAppRun {

    private static boolean running = true;

    public static class MyThread extends Thread {
        public void run() {
            long counter = 0;
            while (running) {
                counter++;
            }

            System.out.println("Counter = " + counter);
            System.out.println(Thread.currentThread().getName() + " exited");
        }
    }


    @Test
    public void testThread() throws InterruptedException {
        MyThread myThread = new MyThread();
        myThread.setName("Controlled Thread");
        myThread.start();

        Thread.sleep(100);

        // TODO: Has no effect???
        running = false;

        Thread.sleep(500);
        System.out.println(Thread.currentThread().getName() + " exited");
        // TODO: App is running and cannot exit. Fix it.
        assertEquals(Thread.State.TERMINATED, myThread.getState());
    }
}
