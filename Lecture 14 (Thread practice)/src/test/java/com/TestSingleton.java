package com;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;


public class TestSingleton {

    static TestSingleton instance;

    public TestSingleton(){}

    public static TestSingleton getInstance(){
        if (instance == null) {
            // TODO: complete
        }
        return instance;
    }

    /**
     * Fill in the gaps and insert instructions to make code executable
     *
     * @throws InterruptedException
     */
    @Test
    public void testThread() throws InterruptedException {
        final AtomicReference<TestSingleton> instance = new AtomicReference<>();

        Thread thread1 = createThread(() -> {
            // TODO: replace with working code
            instance.compareAndSet(null, null);
        });

        thread1.start();

        thread1.join();

        assertEquals(TestSingleton.getInstance(), instance.get());
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