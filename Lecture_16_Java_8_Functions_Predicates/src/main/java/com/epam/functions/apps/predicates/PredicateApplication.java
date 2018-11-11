package com.epam.functions.apps.predicates;

import com.epam.functions.model.SearchRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.epam.functions.util.Preconditions.ArgumentChecks.required;

//@SpringBootApplication
public class PredicateApplication {

    /**
     * Predicate / Functional Interface
     *
     * @param type
     * @param requests
     * @return
     */
    public Collection<SearchRequest> filterSearchRequests(final SearchRequest.SearchRequestType type, final Collection<SearchRequest> requests) {
        required(type, "type");
        Predicate<SearchRequest> filterSearchRequest = (SearchRequest sr) -> sr.getSearchRequestType() == type;
        return requests.stream().filter(filterSearchRequest).collect(Collectors.toList());
    }

    public PredicateApplication() throws Throwable {

        Collection<SearchRequest> srs = new ArrayList<>();

        SearchRequest.Builder builder = SearchRequest.newBuilder()
                .setProduct("Product 1")
                .setRequestId("Request 1")
                .setSearchRequestType(SearchRequest.SearchRequestType.ADD)
                .setUserId(Optional.of("User 1"));
        SearchRequest sr = builder.build();
        srs.add(sr);

        builder = SearchRequest.newBuilder()
                .setProduct("Product 1")
                .setRequestId("Request 1")
                .setSearchRequestType(SearchRequest.SearchRequestType.DELETE)
                .setUserId(Optional.of("User 1"));
        SearchRequest sr2 = builder.build();
        srs.add(sr2);

        builder = SearchRequest.newBuilder()
                .setProduct("Product 1")
                .setRequestId("Request 1")
                .setSearchRequestType(SearchRequest.SearchRequestType.ADD)
                .setUserId(Optional.of("User 1"));

        SearchRequest sr3 = builder.build();
        sr3 = builder.build();
        srs.add(sr3);

        Collection<SearchRequest> filteredRequests = filterSearchRequests(SearchRequest.SearchRequestType.ADD, srs);

        System.out.println("Search Request size = " + filteredRequests.size());
    }

    public static void main(String[] args) throws Throwable {
        //SpringApplication.run(PredicateApplication.class, args);
        new PredicateApplication();
    }


}
