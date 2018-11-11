package prepare.util;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class Util {

    public static void threadSleep(final int mil) throws InterruptedException {
        try {
            Thread.currentThread().sleep(mil);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw e;
//            throw e;
        }
    }

    public static void shutdown(ExecutorService executorService) throws InterruptedException {
        shutdown(executorService, false);
    }

    public static void shutdown(ExecutorService executorService, boolean exit) throws InterruptedException {
        try {
            if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                executorService.shutdown();
                System.out.println("Shutdown");
            }
        } catch (InterruptedException e) {
            List<Runnable> waiting = executorService.shutdownNow();
            waiting.forEach((task) -> {
                System.out.println("Waiting: " + task);
            });
            System.out.println("Shutdown Now");
            System.out.println("Terminated: " + executorService.isTerminated());
            System.out.println("Shutdown Now");
        } finally {
            if (exit) {
                Thread.sleep(1);
                System.exit(0);
            }
        }
    }
}
