package com.epam.functions.model;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.text.DateFormat;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

public class SearchRequestInstant extends Request {

    public enum SearchRequestType {
        ADD,
        NEW,
        DELETE,
        DEFAULT;

        private final String name;

        SearchRequestType() {
            this.name = name().toLowerCase();
        }

        public static SearchRequestType of(final String actionName) {
            return Arrays.stream(values()).filter(item -> item.name.equalsIgnoreCase(actionName)).findFirst().get();
        }

        public String toString() {
            return name;
        }

        public String getName() {
            return name;
        }
    }

    public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.UK).withZone(ZoneOffset.UTC);


    protected SearchRequestType searchRequestType;

    protected Instant createdOn;

    public SearchRequestType getSearchRequestType() {
        return searchRequestType;
    }

    private SearchRequestInstant() {
        this(null, Optional.empty(), Optional.empty(), null);
    }

    public SearchRequestInstant(String product, Optional<String> requestId, Optional<String> userId, SearchRequestType searchRequestType) {
        super(product, requestId, userId);
        this.searchRequestType = searchRequestType;
        this.createdOn = Instant.now();
    }

    protected void validate() throws Throwable {
        super.validate();
        Optional.ofNullable(searchRequestType).orElseThrow(() -> new NullPointerException("SearchRequestType is not defined"));
    }

    public static Builder newBuilder() {
        return newBuilder(SearchRequestType.DEFAULT);
    }

    public static Builder newBuilder(JsonObject obj) {
        return newBuilder(SearchRequestType.DEFAULT);
    }

    public static Builder newBuilder(final SearchRequestType type) {
        return new Builder(type);
    }

    public static final class Builder {
        private final SearchRequestInstant request;

        private Builder(SearchRequestType type) {
            request = new SearchRequestInstant();
            request.searchRequestType = type;
        }

        public SearchRequestInstant build() throws Throwable {
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

        public Builder setCreatedOn(Instant createdOn) {
            request.createdOn = createdOn;
            return this;
        }

        public Builder fromJsonObject(JsonObject object) {
            setCreatedOn(Instant.from(dateFormat.parse(object.getString("cratedOn"))));
            setSearchRequestType(SearchRequestType.of(object.getString("searchRequestType")));
            setRequestId(object.getString("requestId"));
            setUserId(Optional.of(object.getString("userId")));
            return this;
        }
    }

    public JsonObject toJsonObject() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        return builder.add("cratedOn", dateFormat.format(createdOn))
                .add("searchRequestType", searchRequestType.getName())
                .add("product", product)
                .add("requestId", requestId)
                .add("userId", userId.get())
                .build();
    }

    @Override
    public String toString() {
        return "SearchRequest{" +
                "searchRequestType=" + searchRequestType +
                ", product='" + product + '\'' +
                ", requestId='" + requestId + '\'' +
                ", userId=" + userId +
                ", createdOn=" + createdOn +
                '}';
    }
}
