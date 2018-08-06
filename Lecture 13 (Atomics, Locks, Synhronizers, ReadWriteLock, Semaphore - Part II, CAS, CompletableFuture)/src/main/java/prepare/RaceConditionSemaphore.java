package prepare;

import prepare.util.Util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

/**
 * Example: http://tutorials.jenkov.com/java-util-concurrent/semaphore.html
 */
public class RaceConditionSemaphore {


    private static class Resource {

        private Semaphore semaphore;

        public Resource(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        public void connect(String uri) {
            boolean permit = false;
            try {
                permit = semaphore.tryAcquire(1, TimeUnit.SECONDS);
                if (permit) {
                    System.out.println("Connection established to " + uri);
                    Util.threadSleep(1500); // 500
                } else {
                    System.out.println("Connection rejected");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (permit)
                    semaphore.release();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService service = Executors.newFixedThreadPool(10);

        Semaphore semaphore = new Semaphore(5);
        Resource resource = new Resource(semaphore);

        IntStream.range(0, 10).forEach((port) -> {
                    service.submit(() -> {
                        resource.connect("localhost, " + (100 + port));
                    });
                }
        );

        putDown(service, 4);
    }

    private static void putDown(ExecutorService service, int delay) throws InterruptedException {
//        Util.threadSleep(delay);
        service.shutdown(); // reject new threads
        if (!service.awaitTermination(delay, TimeUnit.SECONDS)) {
            service.shutdownNow();
        }
    }
}
