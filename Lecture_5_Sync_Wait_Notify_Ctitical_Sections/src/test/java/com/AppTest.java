package com;

import org.junit.Test;

public class AppTest {

    static class MyRunnable implements Runnable {
        int counter = 0;

        @Override
        public void run() {
            counter++;
        }
    }

    /**
     * TODO: Fix the test.
     * @throws InterruptedException
     */
    @Test
    public void testThread() throws InterruptedException {


    }
}
