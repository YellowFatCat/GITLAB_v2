package com.epam.metrics.connectors.rest;

import org.springframework.http.ResponseEntity;

/**
 * Created by Sergei_Zheleznov on 18.08.2017.
 */
public class ResponseStatusException extends RuntimeException {

    private ResponseEntity<?> response;

    public <T> ResponseStatusException(ResponseEntity<T> response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ResponseStatusException{" +
                "response=" + response +
                '}';
    }
}
