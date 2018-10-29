package com.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Waiter implements Runnable {

    private static final int WAITER_PACE = 2000;
    private CountDownLatch dishesServied;
    private BlockingQueue<String> blockingQueue;

    public Waiter(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public Waiter(BlockingQueue<String> blockingQueue, CountDownLatch dishesServied) {
        this(blockingQueue);
        this.dishesServied = dishesServied;
    }

    @Override
    public void run() {
        while(true) {
            try {
                // TODO: add CountDownLatch code (countDown(), await() if necessary)
                String food = blockingQueue.take();
                System.out.println("Waiters served: " + food);
                Thread.sleep(WAITER_PACE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
