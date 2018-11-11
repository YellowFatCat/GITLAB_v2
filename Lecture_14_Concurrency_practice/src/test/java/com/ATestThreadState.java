package com;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Admin on 8/6/2018.
 */
public class ATestThreadState {

    /**
     * Fill in the gaps and insert instructions to make code executable
     *
     * @throws InterruptedException
     */
    @Test
    public void testThreadState() throws InterruptedException {

        Thread mainThread = Thread.currentThread();

        Thread thread1 = createThread(() -> {
            try {
                mainThread.join(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread thread2 = createThread(() -> {
            try {
                mainThread.join(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // After creation
        assertEquals(Thread.State.NEW, thread1.getState());
        assertEquals(Thread.State.NEW, thread2.getState());

        thread1.start();
        thread2.start();

        // After start
        assertEquals(Thread.State.RUNNABLE, thread1.getState());
        assertEquals(Thread.State.RUNNABLE, thread2.getState());

        Thread.sleep(100);

        // After join with timeout
        assertEquals(Thread.State.TIMED_WAITING, thread1.getState());
        assertEquals(Thread.State.TIMED_WAITING, thread2.getState());

        // Main thread is still runnable
        assertEquals(Thread.State.RUNNABLE, Thread.currentThread().getState());
    }

    private Thread createThread(Runnable runnable) {
        return new Thread(runnable);
    }

}
