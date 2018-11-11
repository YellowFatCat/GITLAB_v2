package com.epam.functions.model;

import java.util.Arrays;
import java.util.Optional;

public class SearchRequest extends Request {

    public enum SearchRequestType {
        ADD,
        NEW,
        DELETE,
        DEFAULT;

        private final String name;

        SearchRequestType() {
            this.name = name().toLowerCase();
        }

        public static Optional<SearchRequestType> of(final String actionName) {
            return Arrays.stream(values()).filter(item -> item.name.equalsIgnoreCase(actionName)).findFirst();
        }

        public String toString() {
            return name;
        }

        public String getName() {
            return name;
        }
    }

    protected SearchRequestType searchRequestType;

    public SearchRequestType getSearchRequestType() {
        return searchRequestType;
    }

    private SearchRequest() {
        this(null, Optional.empty(), Optional.empty(), null);
    }

    public SearchRequest(String product, Optional<String> requestId, Optional<String> userId, SearchRequestType searchRequestType) {
        super(product, requestId, userId);
        this.searchRequestType = searchRequestType;
    }

    protected void validate() throws Throwable {
        super.validate();
        Optional.ofNullable(searchRequestType).orElseThrow(() -> new NullPointerException("SearchRequestType is not defined"));
    }

    public static Builder newBuilder() {
        return newBuilder(SearchRequestType.DEFAULT);
    }

    public static Builder newBuilder(final SearchRequestType type) {
        return new Builder(type);
    }

    public static final class Builder {
        private final SearchRequest request;

        private Builder(SearchRequestType type) {
            request = new SearchRequest();
            request.searchRequestType = type;
        }

        public SearchRequest build() throws Throwable {
            request.validate();
            return request;
        }

        public Builder setProduct(String product) {
            request.product = product;
            return this;
        }

        public Builder setRequestId(String requestId) {
            request.requestId = requestId;
            return this;
        }

        public Builder setUserId(Optional<String> userId) {
            request.userId = userId;
            return this;
        }

        public Builder setSearchRequestType(SearchRequestType searchRequestType) {
            request.searchRequestType = searchRequestType;
            return this;
        }
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "searchRequestType=" + searchRequestType +
                ", product='" + product + '\'' +
                ", requestId='" + requestId + '\'' +
                ", userId=" + userId +
                '}';
    }
}
