package prepare;

import sun.rmi.runtime.RuntimeUtil;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class RaceConditionScheduledExecutorService {


    public static Callable<String> callable(String result, int sleep) {
        return () -> {
            Thread.sleep(sleep);
            System.out.println("  " + result);
            return result;
        };
    }

    public static Runnable runnable(String result, int sleep) {
        return () -> {
            final long start = System.currentTimeMillis();
            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(result + " " + (-start + System.currentTimeMillis()));
        };
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        System.out.println("Start scheduled service");

        ScheduledFuture scheduledFuture = service.schedule(callable("Executed Delayed", 100), 1, TimeUnit.SECONDS);
//        scheduledFuture.getDelay()
        System.out.println("Scheduled delayed result: '" + scheduledFuture.get()  + "'");

        ScheduledFuture<?> future = service.scheduleAtFixedRate(runnable("Executed Fixed Rate", 1000), 1, 1, TimeUnit.SECONDS);

        service.schedule(() -> {
            // executed with 1 sec lag between tasks
            service.scheduleWithFixedDelay(runnable("Executed delayed for 200 ms at 1 sec rate between tasks", 200), 1, 1, TimeUnit.SECONDS);
            System.out.println("Cancel periodic Task Executor");
            future.cancel(true);
        }, 11, TimeUnit.SECONDS);

        System.out.println("end scheduled service");
        putDown(service, 20);
    }

    private static void putDown(ExecutorService service, int delay) throws InterruptedException {
        if (!service.awaitTermination(delay, TimeUnit.SECONDS)) {
            service.shutdownNow();
        }
    }
}
