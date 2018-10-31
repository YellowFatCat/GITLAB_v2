package com.mycompany.prepare;


import com.mycompany.prepare.utils.Utils;

// Mutex, ctitical section in the static method
public class LockReturnOnWaitSleep {

    // how to make it thread safe - thread independent
    static class Counter {
        // isolated Object
        private static int counter = 0;

        public synchronized void inc() throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + " executed. Lock Acquired.");
            wait(1000);
            counter++;
            System.out.println(Thread.currentThread().getName() + " executed. Lock Released.");
        }

        public synchronized void dec() throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + " executed. Lock Acquired.");
            wait(1000);
            counter--;
            System.out.println(Thread.currentThread().getName() + " executed. Lock Released.");
        }


        public synchronized void incSleep() throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + " executed. Lock Acquired.");
            Thread.sleep(1000);
            counter++;
            System.out.println(Thread.currentThread().getName() + " executed. Lock Released.");
        }

        public synchronized void decSleep() throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + " executed. Lock Acquired.");
            wait(1000);
            counter--;
            System.out.println(Thread.currentThread().getName() + " executed. Lock Released.");
        }
    }


    public static void main(String... args) {

        Counter counter = new Counter();
        System.out.println("Wait - lock is not released!!!");

        new Thread(() -> {
            try {
                counter.inc();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                counter.dec();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Utils.sleep(2500);

        System.out.println("Sleep - lock is not released!!!");

        new Thread(() -> {
            try {
                counter.incSleep();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                counter.incSleep();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
