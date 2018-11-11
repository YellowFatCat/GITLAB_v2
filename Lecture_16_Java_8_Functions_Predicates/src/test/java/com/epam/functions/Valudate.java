package com.epam.functions;


import com.google.common.collect.Streams;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
public class Valudate {
    public Valudate() {
    }

    @Test
    public void testSome() {
        BiFunction<Integer, Integer, Two> constructor = Two::new;
        Two two = constructor.apply(1, 1);
        System.out.println("Result: " + two);

        Function<Integer, Two> con = Two::new;
        con.apply(2);

        List<String> first = Arrays.asList("1", "2");
        List<String> second = Arrays.asList("3", "4");

        List<List<String>> combined = Arrays.asList(first, second);

        List<String> full = combined.stream().flatMap(Collection::stream).collect(Collectors.toList());
        full.stream().forEach(System.out::println);

    }
}

class Two {

    public Integer i;
    public Integer j;

    public Two(Integer i) {
        this.i = i;
    }
    public Two(Integer i, Integer j) {
        this.i = i;
        this.j = j;
    }
}