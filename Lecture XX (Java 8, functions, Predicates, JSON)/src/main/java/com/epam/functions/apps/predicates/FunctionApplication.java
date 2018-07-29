package com.epam.functions.apps.predicates;

import com.epam.functions.model.SearchRequest;
import com.google.common.base.Joiner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


//@SpringBootApplication
public class FunctionApplication {

    public static final BiFunction<List<?>, String, String> joiner = (list, separator) ->
            Joiner.on(separator).join(list);

    public static final Function<List<?>, String> joinWithSpace = (list) -> joiner.apply(list, " ");


    public FunctionApplication() {
        List<SearchRequest> requests = generateRandomRequests();
        requests.stream()
                .filter(request -> request.getSearchRequestType() == SearchRequest.SearchRequestType.ADD)
                .forEach(req -> {
                    System.out.println(req.toString());
                });
    }

    private static List<SearchRequest> generateRandomRequests() {
        return IntStream.rangeClosed(6, 10)
                .boxed()
                .flatMap(userId ->
                        IntStream.rangeClosed(0, 5)
                                .mapToObj(requestId -> {
                                    try {
                                        return generateRequest(userId, requestId);
                                    } catch (Throwable throwable) {
                                        throw new RuntimeException(throwable);
                                    }
                                }))
                .collect(Collectors.toList());
    }

    private static SearchRequest generateRequest(int userId, int i) throws Throwable {
        SearchRequest.Builder builder = SearchRequest.newBuilder(new Random().nextInt(2) == 0 ? SearchRequest.SearchRequestType.DELETE : SearchRequest.SearchRequestType.ADD)
                .setUserId(Optional.of("User " + userId))
                .setRequestId("Request " + i)
                .setProduct("Product " + i);
        return builder.build();
    }

    public static void main(String[] args) {
//        SpringApplication.run(FunctionApplication.class, args);
    }
}
