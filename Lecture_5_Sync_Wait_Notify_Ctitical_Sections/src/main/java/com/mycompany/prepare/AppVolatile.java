package com.mycompany.prepare;

// Reads and writes are atomic for primitive types (except for long and double) and atomic for volatile
// If the shared object 'running' is not declared as volatle then it is not guaranteed to be propogated and updated between threadS memory context
public class AppVolatile {

    private static boolean running = true;
//    private static volatile boolean running = true;

    public static class ListenerThread extends Thread {
        public void run() {
            System.out.println("Listener Thread is started");
            long counter = 0;
            while (running) {
                counter++;
            }

            System.out.println("Listener Thread is interrupted");
        }
    }

    public static class SignalThread extends Thread {
        public void run() {
            long counter = 0;
            while (!isInterrupted()) {
                if (running) {
                    running = false;
                    System.out.println("running 'signal' is set " + running);
                }
                counter++;
            }

            System.out.println("Signal Thread completed");
        }
    }



    public static void main(String... args) throws InterruptedException {
        ListenerThread myThread = new ListenerThread();
        SignalThread signalThread = new SignalThread();

        myThread.start();
        Thread.sleep(100);

        signalThread.start();
        Thread.sleep(100);

        signalThread.interrupt();

        System.out.println(Thread.currentThread().getName() + " Exited");
    }
}
