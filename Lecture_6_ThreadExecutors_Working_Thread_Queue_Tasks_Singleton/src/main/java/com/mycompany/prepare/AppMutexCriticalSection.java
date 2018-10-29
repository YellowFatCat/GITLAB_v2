package com.epam.LABSpringBoot.prepare;

import com.epam.LABSpringBoot.prepare.utils.Utils;

// Mutex, ctitical section in the static method
public class AppMutexCriticalSection {

    // how to make it thread safe - thread independent
    static class Counter {
        // isolated Object
        private final Object object = new Object();
        private static int counter = 0;

        public synchronized static void inc() {
            {
                counter++;
            }
        }

        public synchronized static void dec() {
            {
                counter--;
            }
        }
    }


    public static void main(String... args) {

        Counter counter = new Counter();

        new Thread(() -> {
            counter.inc();
        }).start();

        new Thread(() -> {
            counter.dec();
        }).start();

        Utils.sleep(1000);

        System.out.println(Thread.currentThread().getName() + " counter = " + counter.counter);
        System.out.println(Thread.currentThread().getName() + " exited");
    }
}
