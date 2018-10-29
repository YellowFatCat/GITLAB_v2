package com.epam.LABSpringBoot.lecture;


import com.epam.LABSpringBoot.prepare.utils.Utils;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

// Visual VM Thread Dead Lock, Thread Dump
public class DeadLock {

    static class Counter {
        static Object object = new Object();

        Queue queue = new ConcurrentLinkedQueue();

        public void inc() {
            synchronized (object) {
                try {
                    System.out.println("Start waiting...");
                    if (queue.isEmpty()) {
                        object.wait();
                        Object object = queue.poll();
                    }
                    System.out.println("Exit waiting...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void notifyUs() {
            synchronized (object) {
                object.notify();
            }
        }
    }

    public static void main(String... args) {
        Counter counter = new Counter();
        new Thread(counter::inc).start();
        new Thread(counter::inc).start();
        new Thread(counter::inc).start();

        System.out.println("notify");
        Utils.sleep(1000);
        counter.notifyUs();

        System.out.println("Exit");

    }
}
