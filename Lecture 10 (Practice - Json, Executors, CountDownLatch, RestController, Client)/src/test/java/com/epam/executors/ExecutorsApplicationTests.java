package com.epam.executors;

import com.epam.executors.model.Figure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;


/**
 * Prerequisites:
 * - Download shakespire.json from https://www.elastic.co/guide/en/kibana/current/tutorial-load-dataset.html
 * - zip it and put into server into shakespear.json.zip
 * Task:
 *  - Add Controller to provide service to get shakespear.json.zip
 *  - Add a task to the Client to download shakespear.json.zip from Server
 *  - Add a task to unzip shakespear.json.zip
 *  - Add service to Server to accept BASE64 files to decode and search for word occurence
 *  - Add a task to encode json in BASE64 format and send to the server
 *  - Server should accept it, decode and return occurences
 *  - When All Tasks are completed then Client should go down
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExecutorsApplicationTests {

    @Test
    public void contextLoads() throws URISyntaxException, InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI("http://localhost:8080/app/custom");

        String name = "Test msg";
        int size = 10;


        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        ExecutorService executorService = Executors.newCachedThreadPool();

        List<Future> requests = new ArrayList();




        executorService.shutdown();
    }

    private Figure sendRequest(RestTemplate restTemplate, URI uri, HttpEntity httpEntity, int i) {
        ResponseEntity<Figure> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Figure.class);
        Figure respFigure = response.getBody();
        System.out.println("Response : " + respFigure + ", code = " + response.getStatusCode());
        return respFigure;
    }

    private Figure generateFigure(String name, int size) {
        Figure figure = new Figure(name, size);
        return figure;
    }

}
