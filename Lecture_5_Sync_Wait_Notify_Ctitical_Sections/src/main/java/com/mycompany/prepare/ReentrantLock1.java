package com.mycompany.prepare;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class ReentrantLock1 {

    public static void main(String... args) throws InterruptedException {

//        Count c = new Count();
        CountLock c = new CountLock();

        new Thread(() -> {
            IntStream.range(0, 1000).forEach((x) -> c.inc());
        }).start();
        new Thread(() -> {
            IntStream.range(0, 1000).forEach((x) -> c.inc());
        }).start();

        Thread.sleep(1000);

        c.print();

        System.out.println("Exit main");

    }


    private static class Count {

        private Object lockObject = new Object();
        int counter = 0;

        public void inc() {
            synchronized (lockObject) {
                counter++;
            }
        }

        public void print(){
            System.out.println("Counter = " + counter);
        }
    }

    /**
     * Equivalent
     */
    private static class CountLock {

        private Lock lock = new java.util.concurrent.locks.ReentrantLock();
        int counter = 0;

        public void inc() {
            lock.lock();
            try {
                counter++;
            } finally {
                lock.unlock();
            }
        }

        public void print(){
            System.out.println("Counter = " + counter);
        }
    }
}
