package com.epam.executors.controller;

import com.epam.executors.model.Figure;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/app")
public class CustomController {

    @RequestMapping(value = "/custom", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Figure custom(@RequestBody Figure figure) throws InterruptedException, JsonProcessingException {
        System.out.println("Controller Received request: " + figure);
        Thread.sleep(1000);

        ObjectMapper mapper = new ObjectMapper();
        String strFigure = mapper.writeValueAsString(figure);
        return figure;
    }
}