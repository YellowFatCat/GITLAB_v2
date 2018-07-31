package com.epam.LABSpringBoot.prepare.queues;

import com.epam.LABSpringBoot.prepare.utils.Utils;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Waiter implements Runnable {

    private BlockingQueue<String> window;

    public Waiter(BlockingQueue<String> window) {
        this.window = window;
    }

    @Override
    public void run() {
        while(true) {
            System.out.println("start waiting");
            try {
                String dish = window.take();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("serving");
            Utils.sleep(1000 + new Random().nextInt(2000));
        }
    }
}
