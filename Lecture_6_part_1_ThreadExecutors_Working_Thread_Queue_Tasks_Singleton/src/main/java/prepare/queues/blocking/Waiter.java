package prepare.queues.blocking;

import prepare.util.Util;

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
            System.out.println("Waiter: start waiting");
            try {
                String dish = window.take(); // blocking call {@link http://tutorials.jenkov.com/java-util-concurrent/blockingqueue.html}
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Waiter: serving");
            Util.sleep(1000 + new Random().nextInt(2000));
        }
    }
}
