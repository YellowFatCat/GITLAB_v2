package practice.com.task;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ZInfiniteAppRunAnswer {

    private static volatile boolean running = true;

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

        running = false;

        Thread.yield();
        Thread.sleep(500);
//        myThreadInterrupted.interrupt();
        System.out.println(Thread.currentThread().getName() + " exited");
        assertEquals(Thread.State.TERMINATED, myThread.getState());
    }
}
