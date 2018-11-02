package com.epam.LABSpringBoot1.controllers;

import com.epam.LABSpringBoot1.model.Figure;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomController {

    @RequestMapping(value = "/custom", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Figure custom(Figure figure) {
        return figure;
    }
}