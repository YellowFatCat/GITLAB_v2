package com;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Lambda1 {


    /**
     * TODO: Convert List of strings to the list of Upper Case strings
     * "Berlin", "Machine" -> "BERLIN", "MACHINE"
     */
    @Test
    public void testUpperCase() {
        List<String> list = new ArrayList<String>();
        list.add("Berlin");
        list.add("Work");
        list.add("speed");
        List<String> result =  list.stream()
                .map(str -> str) // TODO: replace with other lambda '::' expression
                .collect(Collectors.toList());


        assertArrayEquals(Arrays.asList("BERLIN", "WORK", "SPEED").toArray(), result.toArray());
    }


    /**
     * Count words in all sentences and sum it up
     */
    @Test
    public void testCountAllWordsInAllSentences() {
        List<String> list = new ArrayList<String>();
        list.add("Berlin One");
        list.add("Work Two Thres");
        list.add("speed man man");

        long sum1 = list.stream().mapToInt(str->{
            return 1; // TODO: replace '1' with lambda expression
        }).sum();

        Long sum = sum1;
        assertEquals(sum, Long.valueOf("8"));

    }
}
