package com.epam.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class TestUtil {
    public static void shutdownWithDelay(ExecutorService service, int timeout) {

        try {
            if (!service.awaitTermination(timeout, TimeUnit.MILLISECONDS)) {
                service.shutdownNow();
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
            service.shutdownNow();
        }
    }
}
