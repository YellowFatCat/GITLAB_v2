package com.epam.functions.model;

import org.apache.commons.lang3.StringUtils;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


public abstract class Request {
    protected String product;
    protected String requestId;
    protected Optional<String> userId;
    private Instant startTime = Instant.now();

    public Request() {
    }

    public Request(final String product, final Optional<String> requestId, final Optional<String> userId) {
        StringUtils.isNotBlank(product);
        this.product = product;
        this.requestId = requestId.orElseGet(() -> StringUtils.remove(UUID.randomUUID().toString(), "-"));
        this.userId = userId;
    }

    protected void validate() throws Throwable {
        StringUtils.isNoneEmpty(product);
        StringUtils.isNoneEmpty(requestId);
        userId.orElseThrow(() -> {
            throw new IllegalArgumentException("User Id is not defined");
        });
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Optional<String> getUserId() {
        return userId;
    }

    public void setUserId(Optional<String> userId) {
        this.userId = userId;
    }

    public Instant getStartTime() {
        return startTime;
    }

    @Override
    public String toString() {
        return "Request{" +
                "product='" + product + '\'' +
                ", requestId='" + requestId + '\'' +
                ", userId=" + userId +
                ", startTime=" + startTime +
                '}';
    }
}
