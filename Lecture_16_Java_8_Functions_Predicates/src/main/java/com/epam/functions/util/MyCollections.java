package com.epam.functions.util;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MyCollections {
    //PECS
    public static<T> void copy(List<? extends T> source, List<? super T> destination) {
        Optional.ofNullable(source).orElseThrow(() -> new IllegalArgumentException("The parameter is null"));
//        destination.addAll(source.stream().collect(Collectors.toCollection(Collectors.toList());
        Collection<?> collection = Lists.newArrayList();

    }
}
