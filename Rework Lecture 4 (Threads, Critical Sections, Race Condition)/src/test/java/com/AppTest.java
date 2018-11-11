package com;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AppTest {

    static class MyRunnable implements Runnable {
        int counter = 0;

        @Override
        public void run() {
            counter++;
        }
    }

    @Test
    public void testThread() {

        MyRunnable myRunnable = new MyRunnable();

        //

        assertEquals(2, myRunnable.counter);
    }


    Thread createThread(MyRunnable myRunnable) {
        return new Thread(myRunnable);
    }
}
