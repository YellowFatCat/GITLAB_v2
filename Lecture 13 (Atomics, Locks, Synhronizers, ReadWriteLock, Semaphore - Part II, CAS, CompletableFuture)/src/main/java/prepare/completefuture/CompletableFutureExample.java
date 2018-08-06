package prepare.completefuture;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureExample {
    public static void main(String[] args) {
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
                    return " Task 1 is completed \n";
                }
        ).thenApplyAsync((result) -> {
            return result + " Task 2 es completed \n";
        })
                .thenApplyAsync((result1) -> {
                    System.out.println("Result is printed: \n" + result1);
                    return result1;
                });
    }
}
