package com.mycompany.queue;

import com.mycompany.prepare.utils.Utils;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;

public class Cook implements Runnable {

    private static final int COOK_PACE = 1000;
    private SynchronousQueue<String> blockingQueue;

    public Cook(SynchronousQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while(true) {
            try {
                blockingQueue.put("Food");
                System.out.println("Cook prepared");
                System.out.println("Queue size = " + blockingQueue.size());
                Utils.sleep(COOK_PACE + new Random().nextInt(COOK_PACE));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
