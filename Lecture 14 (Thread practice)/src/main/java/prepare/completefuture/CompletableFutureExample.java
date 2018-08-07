package prepare.completefuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                    return " Task 1 is completed \n";
                }
        ).thenApplyAsync((result) -> {
            return result + " Task 2 is completed \n";
        }).thenApplyAsync((result1) -> {
                    System.out.println("Result is printed: \n" + result1);
                    return result1;
                });

        System.out.println("Part 1 : \n" + future.get());


        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            runTask();
            return "Completed Task 1";
        });

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            runTask();
            return "Completed Task 2";
        });

        CompletableFuture<Object> result = CompletableFuture.anyOf(future1, future2);
        Object resultObject = result.get();
        System.out.println("Result - " + resultObject);
    }

    private static void runTask() {
        try {
            Thread.currentThread().sleep(1000 + new Random().nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
