package com.epam.functions.apps.predicates;

import com.google.common.base.Joiner;
import org.springframework.boot.SpringApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;


//@SpringBootApplication
public class FunctionUtilApplication {


    /**
     *
     */
    public FunctionUtilApplication() throws InterruptedException {
        parallelStream();
    }

    private void parallelStream() throws InterruptedException {
        String[] strs = new String[]{"One", "Two", "Three", "Four"};
        Collection<String> myStrs = Collections.synchronizedList(new ArrayList<String>());
//
//        synchronized (this) {
//            wait(100);
//        }

        Stream.of(strs).parallel().forEach(i -> {
            System.out.println("i: " + i);
            myStrs.add(i);
        });
        System.out.println("Joiner Size " + strs.length);
        System.out.println("Joiner: " + Joiner.on(", ").join(myStrs));

    }

    private void filterString() {
        String[] strs = new String[]{"One", "Two", "Three", "Four"};
        List<String> line = Arrays.asList(strs);
        line.stream().filter(str -> str.length() > 0).findFirst().ifPresent(System.out::println);
    }

    /**
     * Joiner for the string
     */
    public static void joinString() {
        String[] args = new String[]{"One", "2", "3", "Four"};
        Collection<String> list = new ArrayList<>(Arrays.asList(args));

        String str = Joiner.on(", ").join(args);
        System.out.println("String: " + str);

        String collectionListString = Joiner.on(", ").join(list);
        System.out.println("Collection string: " + collectionListString);

    }

    public static void main(String[] args) {
        SpringApplication.run(FunctionUtilApplication.class, args);
    }
}
