package com;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class VolatileTest extends Thread {

    private static volatile int MY_INT = 0;
    public static final int WAIT = 5;

    static class ChangeListener extends Thread {
        @Override
        public void run() {
            int local_value = MY_INT;
            while (local_value < WAIT) {
                if (local_value != MY_INT) {
                    System.out.println("Got Change for MY_INT : " + MY_INT);
                    local_value = MY_INT;
                }
            }
        }
    }

    static class ChangeMaker extends Thread {
        @Override
        public void run() {

            int local_value = MY_INT;
            while (MY_INT < WAIT) {
                System.out.println(String.format("Incrementing MY_INT to : %d", local_value + 1));
                MY_INT = ++local_value;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * TODO: use volatile and explain why it fixes the problem?
     * @throws InterruptedException
     */
    @Test
    public void test() throws InterruptedException {
        Thread listener = new ChangeListener();
        Thread maker = new ChangeMaker();

        listener.start();
        maker.start();

        listener.join(1000 * WAIT);

        assertEquals(5, MY_INT);
        assertEquals(listener.getState(), State.TERMINATED );
    }
}
