package com.mycompany.prepare;


// Visual VM Thread Dead Lock, Thread Dump
public class DoubleSyncEntry {

    static class MyCounter {

        private Object o1 = new Object();

        int counter = 0;

        public void inc() {

            synchronized (o1){
                System.out.println(counter++);
                if (counter == 100000) {
                    return;
                }
                inc();
                // Can we process StackOverflow?
            }
        }
    }

    public static void main(String... args) {
        MyCounter counter = new MyCounter();

        new Thread(() -> {
                counter.inc();
        }).start();

        System.out.println("Exit");
    }
}
