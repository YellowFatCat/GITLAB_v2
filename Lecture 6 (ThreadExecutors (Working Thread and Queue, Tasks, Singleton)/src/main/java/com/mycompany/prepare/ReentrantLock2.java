package com.epam.LABSpringBoot.prepare;


import java.util.concurrent.locks.ReentrantLock;

// Visual VM Thread Dead Lock, Thread Dump
public class ReentrantLock2 {

    static class MyCounter {

        private Object o1 = new Object();
        private Object o2 = new Object();

        int counter = 0;

        public void inc() throws InterruptedException {
            synchronized (o1) {
                o1.wait(100);
                synchronized (o2) {
                    o2.wait(100);

                    counter ++;
                }
            }
        }

        public void dec() throws InterruptedException {
            synchronized (o2) {
                o2.wait(100);
                synchronized (o1) {
                    o1.wait(100);

                    counter --;
                }
            }
        }
    }

    public static void main(String... args) {
//        int counter = 0;
//        Object object = new Object();
//
//        synchronized (object) {
//            counter = 1;
//            method();
//        }
//
        ReentrantLock lock = new ReentrantLock();



        lock.lock();
        try{
//            counter = 1;
            method();
        } finally {
            lock.unlock();
        }



        MyCounter counter = new MyCounter();

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

        System.out.println("Exit");
    }

    private static void method() {
        throw new RuntimeException();
    }
}
