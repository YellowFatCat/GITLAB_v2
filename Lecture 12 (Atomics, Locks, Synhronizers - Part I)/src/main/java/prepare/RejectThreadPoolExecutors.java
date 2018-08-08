package prepare;

import prepare.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class RejectThreadPoolExecutors {

    private static final AtomicInteger atomicInt = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

//        executorService.submit(App::increment);
//        executorService.submit(App::increment);
        List<Callable<Integer>> promise = new ArrayList<>();

        IntStream.range(0, 10).forEach(i -> promise.add(i, () -> {
            try {
                Util.threadSleep(1000);
                System.out.println(i);
            } catch (Throwable e) {
                e.printStackTrace();
            }
            return i;
        }));


        new Thread(() -> {
            try {
                Util.threadSleep(2000);
                RejectThreadPoolExecutors.shutdown(executorService);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // It will be rejected
        executorService.invokeAll(promise);
        // wait till first is completed
        executorService.invokeAll(promise);
    }

    private static void shutdown(ExecutorService executorService) throws InterruptedException {
        try {
            System.out.println("Shutdown");
            executorService.shutdown();
            if (!executorService.awaitTermination(2, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
//                System.exit(1);
                System.out.println("Shutdown");
            }

//            executorService.shutdownNow(); // Syste.exit(1) - use exit instead
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
