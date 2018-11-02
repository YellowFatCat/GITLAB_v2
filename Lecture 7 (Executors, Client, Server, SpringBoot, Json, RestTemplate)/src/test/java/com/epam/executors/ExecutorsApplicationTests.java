package com.epam.executors;

import com.epam.executors.controllers.Figure;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ExecutorsApplicationTests {

    @Test
    public void callCustom() throws URISyntaxException, InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI("http://localhost:8080/custom");

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        ExecutorService executorService = Executors.newCachedThreadPool();

        Figure figure = new Figure();
        figure.name = "My Name.";
        figure.size = 1;
        HttpEntity<Figure> httpEntity = new HttpEntity<Figure>(figure);

        ResponseEntity<Figure> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Figure.class);

        System.out.println("Response: body" + response.getBody());
        System.out.println("Response: " + response);
    }
}
