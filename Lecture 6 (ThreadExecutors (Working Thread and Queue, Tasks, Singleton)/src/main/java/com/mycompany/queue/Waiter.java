package com.epam.LABSpringBoot.queue;

import com.epam.LABSpringBoot.prepare.utils.Utils;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class Waiter implements Runnable {

    private static final int WAITER_PACE = 2000;
    private SynchronousQueue<String> blockingQueue;

    public Waiter(SynchronousQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    @Override
    public void run() {
        while(true) {
            try {
                String food = blockingQueue.take();
                System.out.println("Waiter: Cook prepared: " + food);
                Utils.sleep(WAITER_PACE + new Random().nextInt(WAITER_PACE));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
