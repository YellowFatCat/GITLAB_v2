package prepare.queues.blocking.prepare;

import prepare.util.Util;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class App {

    private static final int TIMEOUT = 100;

    private static class Producer implements Runnable {

        BlockingQueue<String> queue;

        public Producer(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            IntStream.range(0, 100).forEach((x) -> {
                final String str = "Send Message: " + x;
                try {
                    Util.sleep(10);
                    queue.put(str);
                    System.out.println(str);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static class Consumer implements Runnable {

        BlockingQueue<String> queue;

        public Consumer(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            IntStream.range(0, 100).forEach((x) -> {
                String str = null;
                try {
                    Util.sleep(10);
                    str = queue.take();
                    System.out.println("Read : " + str);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new App();

        BlockingQueue<String> queue = new SynchronousQueue<>();

        Producer prod1 = new Producer(queue);
//        Producer prod2 = new Producer(queue);
        Consumer consumer = new Consumer(queue);

        ExecutorService service = Executors.newFixedThreadPool(3);

        service.submit(prod1);
//        service.submit(prod2);
        service.submit(consumer);


        shutdown(service);
    }

    private static void shutdown(ExecutorService service) throws InterruptedException {
        service.shutdown();
        service.awaitTermination(2000, TimeUnit.MILLISECONDS);
        service.shutdownNow();
    }
}
