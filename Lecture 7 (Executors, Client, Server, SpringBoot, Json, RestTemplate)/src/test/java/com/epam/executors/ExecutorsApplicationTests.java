package com.epam.executors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.concurrent.*;




@RunWith(SpringRunner.class)
@SpringBootTest
public class ExecutorsApplicationTests {

    private static final String MATCH_EXPR = "Hello";

    @Test
    public void findMatchingLinesOccurences() throws URISyntaxException, InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI("http://localhost:8080/app/str");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        ExecutorService executorService = Executors.newCachedThreadPool();


        HttpEntity<String> httpEntity = new HttpEntity<String>("This is the test message");

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);

        System.out.println("Response: " + response);
    }
}
