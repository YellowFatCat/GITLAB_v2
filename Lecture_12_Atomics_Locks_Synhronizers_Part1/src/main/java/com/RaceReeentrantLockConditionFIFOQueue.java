package com;


import prepare.util.Util;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;


public class RaceReeentrantLockConditionFIFOQueue {

    private static class FIFO {
        Queue<String> fifoQueue = new ArrayDeque<>();
        int CAPACITY = 10;

        ReentrantLock lock = new ReentrantLock();
        Condition fifoEmpty = lock.newCondition();
        Condition fifoFull = lock.newCondition();

        public void offer(String item) throws InterruptedException {
            lock.lock();
            try {
                while (fifoQueue.size() == CAPACITY) {
                    fifoFull.await();
                }
                fifoQueue.offer(item);
            } finally {
                fifoEmpty.signalAll();
                lock.unlock();
            }
        }

        public String pool() throws InterruptedException {
            lock.lock();
            try {
                while (fifoQueue.size() == 0) {
                    fifoEmpty.await();
                }
                return fifoQueue.poll() + "; Queue size = " + (1 + fifoQueue.size());
            } finally {
                fifoFull.signalAll();
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        FIFO fifo = new FIFO();

        executorService.submit(() -> {
            IntStream.range(0, 1000).forEach((i) -> {
                        try {
//                            Util.threadSleep(1);
                            fifo.offer(String.valueOf(i));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
        });

        executorService.submit(() -> {
            IntStream.range(0, 1000).forEach((i) -> {
                        try {
//                            Util.threadSleep(1);
                            System.out.println(fifo.pool());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
        });

        Util.threadSleep(2000);
        putDown(executorService);
    }

    private static void putDown(ExecutorService executorService) throws InterruptedException {
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.SECONDS);
    }


}
