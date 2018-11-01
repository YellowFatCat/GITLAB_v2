package prepare.queues.blocking;

import prepare.util.Util;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Cook implements Runnable {

    private BlockingQueue<String> window;

    public Cook(BlockingQueue<String> window) {
        this.window = window;
    }

    @Override
    public void run() {
        while(true) {
            System.out.println("Cook: start cooking");
            Util.sleep(1000 + new Random().nextInt(1000));
            System.out.println("Cook: dish is ready, waiting for waiter");

            try {
                window.put("dish");  // blocking call {@link http://tutorials.jenkov.com/java-util-concurrent/blockingqueue.html}
                System.out.println("Window size: " + window.size());
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
