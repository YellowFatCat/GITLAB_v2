package practice.com.task;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestThreadStopAnswer {

    static class Manageable extends Thread {

        volatile public static boolean running = true;
        public static String str = "";

        @Override
        public void run() {

            while (running) {
                try {
                    str = str + "a";
                    synchronized (this) {
                        wait(100);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testThreadState() throws InterruptedException {

        Manageable thread = new Manageable();
        assertEquals(thread.getState(), Thread.State.NEW);
        thread.start();
        assertEquals(thread.getState(), Thread.State.RUNNABLE);


        for (int i = 0; i < 100; i++) {
            if (Manageable.str.equals("aaa")) {
                TestThreadStopAnswer.Manageable.running = false;
                break;
            }
            synchronized (this) {
                try {
                    wait(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Received : " + Manageable.str);
        assertEquals("aaa", Manageable.str);
    }
}
