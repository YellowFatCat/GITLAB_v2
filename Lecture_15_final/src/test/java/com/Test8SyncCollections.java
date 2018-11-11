package com;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.SocketHandler;
import java.util.stream.IntStream;

public class Test8SyncCollections {

    @Test
    public void testSyncList() throws InterruptedException {
        List<String> original = new ArrayList<>();
        original.add("Vova");

        List<String> list = Collections.unmodifiableList(original);

        ExecutorService service = Executors.newFixedThreadPool(2);

        try {
            service.submit(() -> {
                try {
                    IntStream.range(0, 10).forEach((i) -> {
                        try {
                            System.out.println("Andrey");
//                            list.set(0, "Andrey");
                            new Socket().connect(new InetSocketAddress(0));
                        } catch (IOException e) {
                            throw new RuntimeException("Andrey", e);
                        }
                    });
                } catch (Throwable e) {
                    System.out.println("Andrey Intercepted");
                    e.printStackTrace();
                    throw e;
                }
            });
        } catch (Throwable e) {
            System.out.println("Andrey Intercepted Intercepted");
            e.printStackTrace();
        }

        try {
            service.submit(() -> {
                try {
                    IntStream.range(0, 10).forEach((i) -> {
                        try {
                            System.out.println("Evgenii");
//                            list.set(0, "Andrey");
                            new Socket().connect(new InetSocketAddress(0));
                        } catch (IOException e) {
                            throw new RuntimeException("Evgenii", e);
                        }
                    });
                } catch (Throwable e) {
                    System.out.println("Evgenii Intercepted");
                    e.printStackTrace();
                    throw e;
                }
            });
        } catch (Throwable e) {
            System.out.println("Evgenii Intercepted Intercepted");
            e.printStackTrace();
        }


        Thread.sleep(1000);
//        service.shutdown();
        System.out.println(Arrays.toString(list.toArray()));
    }
}
