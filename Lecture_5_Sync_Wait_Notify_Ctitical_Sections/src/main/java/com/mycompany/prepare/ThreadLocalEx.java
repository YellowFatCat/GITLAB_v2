package com.mycompany.prepare;

import java.util.stream.IntStream;

// The onject can be instantiated per the concrete Thread Stack memory and utilized (retrieved) by demand in accordance to
// the ThreadLocal == Map<Thread, Object> sense
public class ThreadLocalEx {
    private static class CountContext { // per thread
        public int count = 0; // like thread connection
    }

    public static class CounterFactory {

        private static ThreadLocal<ThreadLocalEx.CountContext> local = new ThreadLocal<ThreadLocalEx.CountContext>() {
            @Override
            protected ThreadLocalEx.CountContext initialValue() {
                return new ThreadLocalEx.CountContext();
            }
        };

        public static ThreadLocalEx.CountContext getCounter() {
            return local.get();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ThreadLocalEx();


        new Thread(() -> {
            IntStream.range(0, 1000).forEach((x) -> {
                ThreadLocalEx.CounterFactory.getCounter().count++;
            });
            System.out.println("Thread : " + Thread.currentThread().getName() + "; Counter= " +ThreadLocalEx.CounterFactory.getCounter().count);
        }).start();

        new Thread(() -> {
            IntStream.range(0, 1000).forEach((x) -> {
                ThreadLocalEx.CounterFactory.getCounter().count++;
            });
            System.out.println("Thread : " + Thread.currentThread().getName() + "; Counter= " +ThreadLocalEx.CounterFactory.getCounter().count);
        }).start();

        Thread.sleep(1000);
    }
}
