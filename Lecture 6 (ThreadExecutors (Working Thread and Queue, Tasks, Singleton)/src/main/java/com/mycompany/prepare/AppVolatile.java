package com.epam.LABSpringBoot.prepare;

import com.epam.LABSpringBoot.prepare.utils.Utils;

// volatile
// interrupted thread
public class AppVolatile {

//    private static boolean running = true;
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

    public static class MyThreadInterrupted extends Thread {
        public void run() {
            long counter = 0;
            while (!isInterrupted()) {
                counter++;
            }

            System.out.println("Counter interrupted = " + counter);
            System.out.println(Thread.currentThread().getName() + " exited");
        }
    }



    public static void main(String... args) {
        MyThread myThread = new MyThread();
//        MyThreadInterrupted myThreadInterrupted = new MyThreadInterrupted();

        myThread.setName("FlagControlled Thread");
//        myThreadInterrupted.setName("Interruptable Thread");

//        myThreadInterrupted.start();
        myThread.start();
        Utils.sleep(1000);

        running = false;

        Utils.sleep(1000);
//        myThreadInterrupted.interrupt();

        System.out.println(Thread.currentThread().getName() + " exited");
    }
}
