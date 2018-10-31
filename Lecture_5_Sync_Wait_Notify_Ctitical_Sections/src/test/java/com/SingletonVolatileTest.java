package com;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class SingletonVolatileTest {

    private static class SingletonVolatile {
        private AtomicInteger counter = new AtomicInteger();
        private static /*TODO: fix here*/ SingletonVolatile instance = null;

        private SingletonVolatile() {
        }

        public void inc() {
            counter.incrementAndGet();
        }

        public static SingletonVolatile getInstance() {
            //TODO: Fix it here
            if (instance == null) {
                try {
                    Thread.sleep(100); // keep sleep()
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                instance = new SingletonVolatile();
            }
            return instance;
        }

        public int getCounter() {
            return counter.get();
        }

        public void setCounter(int i) {
            counter.set(i);
        }
    }

    @Test
    public void testSingleton() throws InterruptedException {

        for (int i = 0; i < 10; i++) {
            SingletonVolatile.getInstance().setCounter(0);

            new Thread(() -> {
                IntStream.range(0, 1000).forEach((x) -> {
                    SingletonVolatile.getInstance().inc();
                });
            }).start();

            new Thread(() -> {
                IntStream.range(0, 1000).forEach((x) -> {
                    SingletonVolatile.getInstance().inc();
                });
            }).start();

            Thread.sleep(500);

            assertEquals(2 * 1000, SingletonVolatile.getInstance().getCounter());
        }
    }
}