package prepare;

import prepare.util.Util;

/**
 * How to Stop Thread (add boolean flag), interrupt
 * Priority
 */
public class AppRestTemplate {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("MyThread :" + Thread.currentThread().getName());
            while(!isInterrupted())
                Util.threadSleep(10000);
        }
    }


    public static void main(String[] args) {
        final Thread thread = new Thread(new MyThread());
        thread.start();
        thread.interrupt();

        // outdated version
        thread.suspend();
        thread.resume();
    }
}
