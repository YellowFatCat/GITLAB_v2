package practice;

import prepare.util.Util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ResourceSemaphore {


    static class ServerResource {
        Semaphore semaphore;

        public ServerResource(Semaphore semaphore) {
            this.semaphore = semaphore;
        }

        public boolean execute() throws InterruptedException {
            boolean executed = false;

            if (semaphore.tryAcquire(1, 200, TimeUnit.MILLISECONDS)) {
//                printState(connected);
                try {
                    executeJob();
                } finally {
                    semaphore.release(1);
                    System.out.println("Executed Job");
                    executed = true;
                }
            }

            return executed;
        }

        private void printState(boolean connected) {
            System.out.println(Thread.currentThread().getName() + "; Connected = " + connected);
        }

        private void executeJob() {
            try {
                Util.threadSleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(5);
        ServerResource resources = new ServerResource(semaphore);
        ExecutorService service = Executors.newFixedThreadPool(10);

        IntStream.range(0, 10).forEach((i) -> {
            Boolean executed = false;
            service.submit(resources::execute);
            System.out.println(i + " : " + Thread.currentThread().getName() + "; Executed: " + executed);
        });

        service.shutdown();
    }
}
