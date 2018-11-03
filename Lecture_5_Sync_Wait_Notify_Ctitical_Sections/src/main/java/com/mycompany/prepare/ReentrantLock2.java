package com.mycompany.prepare;

import com.mycompany.prepare.utils.Utils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// Reentrant Lock replacement for synchronized in heavy operations
// Release of lock and advantages.
// liveness
public class ReentrantLock2 {

    // how to make it thread safe - thread independent
    static class Counter {
        // intrisic lock
//        private final Object object = new Object();
        private int counter = 0;

//
//        public void inc() {
//            synchronized (object) {
//                counter++;
//            }
//        }


        // External lock
        Lock lock = new ReentrantLock();

        public void inc() throws InterruptedException {
            System.out.println(Thread.currentThread().getName() + " attempt acquire lock");
            if(lock.tryLock(1000, TimeUnit.MILLISECONDS)) {
                System.out.println(Thread.currentThread().getName() + " acquired lock");
                try {
                    Utils.sleep(100); // liveness -> use tryLock() for heavy operations (while (true) to avoid starvation
                    counter++;
                } finally {
                    System.out.println(Thread.currentThread().getName() + " release lock");
                    lock.unlock();
                }
            }
        }
    }

    public static void main(String ... args) {

        Counter counter = new Counter();

        new Thread(() -> {
            try {
                counter.inc();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread 1").start();

        new Thread(() -> {
            try {
                counter.inc();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "Thread 2").start();

        Utils.sleep(2200);

        System.out.println(Thread.currentThread().getName() + " counter = " + counter.counter);
        System.out.println(Thread.currentThread().getName() + " exited");
    }
}
