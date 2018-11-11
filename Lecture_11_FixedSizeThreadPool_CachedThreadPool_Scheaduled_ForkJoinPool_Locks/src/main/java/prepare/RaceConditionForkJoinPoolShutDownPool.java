package prepare;

import prepare.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class RaceConditionForkJoinPoolShutDownPool {


    public static Callable<Integer> callable(int result, int sleep) {
        return () -> {
            Thread.sleep(sleep);
            return result;
        };
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        ExecutorService service = Executors.newWorkStealingPool();

        List<Callable<Integer>> callable = Arrays.asList(
                callable(1, 100),
                callable(2, 0 /*10*/), // play around
                callable(3, 100)
        );


//        List<String> result = Arrays.asList("Name 1", "Name 2");
//        result.parallelStream().forEach(
//                (String s) ->
//                {
//                    System.out.println(s);
//                }
//        );
//


        int result = service.invokeAny(callable); // returns the first task being completed

        // !!! If threads are started then we need to enforce it and shut it down
        //
        System.out.println("Counter = " + result);
        System.out.println("The process is started and completed");

        putDown(service, 2);
    }

    /**
     * Refer to ExecutorService shutdown recommendations in docs
     */
    private static void putDown(ExecutorService service, int delay) throws InterruptedException {
        if (!service.awaitTermination(delay, TimeUnit.SECONDS)) {
            service.shutdownNow();
        }
    }
}
