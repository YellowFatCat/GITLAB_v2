package com;

import prepare.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class RaceReeentrantLockWithCondition {

    private static class SyncStack {
        Stack<Integer> stack = new Stack();
        final static int CAPACITY = 10;
        ReentrantLock lock = new ReentrantLock();

        Condition readCondition = lock.newCondition();
        Condition writeCondition = lock.newCondition();

        public void put(Integer obj) throws InterruptedException {
            lock.lock();
            try {
                if (stack.size() == CAPACITY) {
                    writeCondition.await();
                }
                stack.push(obj);
            } finally {
                readCondition.signalAll();
                lock.unlock();
            }
        }

        public Integer pop() throws InterruptedException {
            lock.lock();
            try {
                if (stack.size() == 0) {
                    readCondition.await();
                }
                return stack.pop();
            } finally {
                writeCondition.signalAll();
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        SyncStack syncStack = new SyncStack();

        executorService.submit(() -> {
            IntStream.range(0, 1000).forEach((i) -> {
                        try {
                            syncStack.put(i);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
        });

        executorService.submit(() -> {
            IntStream.range(0, 1000).forEach((i) -> {
                        try {
                            System.out.println(syncStack.pop());
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
