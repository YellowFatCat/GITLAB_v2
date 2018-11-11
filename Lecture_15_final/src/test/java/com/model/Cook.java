package com.model;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;

public class Cook implements Runnable {

    private static final int COOK_PACE = 1000;
    private final CountDownLatch dishesPrepared;
    private BlockingQueue<String> blockingQueue;

    public Cook(BlockingQueue<String> blockingQueue, CountDownLatch dishesPrepared) {
        this.blockingQueue = blockingQueue;
        this.dishesPrepared = dishesPrepared;
    }

    @Override
    public void run() {
        while(true) {
            try {
                // TODO: add CountDownLatch code (countDown(), await() if necessary)
                blockingQueue.put("Food");
                System.out.println("Cook prepared");
                System.out.println("Queue size = " + blockingQueue.size());
                Thread.sleep(COOK_PACE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
