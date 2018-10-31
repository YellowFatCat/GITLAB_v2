package com.mycompany.prepare;


/**
 * How to Stop Thread (add boolean flag), interrupt
 * Priority
 */
public class Interrupt {

    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("ListenerThread :" + Thread.currentThread().getName());
            boolean run = true;
            while(!isInterrupted() && run) {
//                Utils.sleep(1000);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    run = false;
                }
                System.out.println("Tick ---");
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        final Thread thread = new Thread(new MyThread());
        thread.start();

        Thread.sleep(3000);
        thread.interrupt();

        // outdated version
//        thread.suspend();
//        thread.resume();
    }
}
