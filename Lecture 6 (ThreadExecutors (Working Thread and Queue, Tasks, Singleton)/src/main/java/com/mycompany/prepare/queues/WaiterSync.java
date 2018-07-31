package com.epam.LABSpringBoot.prepare.queues;

import com.epam.LABSpringBoot.prepare.utils.Utils;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class WaiterSync implements Runnable {

    private SynchronousQueue<String> window;

    public WaiterSync(SynchronousQueue<String> window) {
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
            Utils.sleep(10000 + new Random().nextInt(2000));
        }
    }
}
