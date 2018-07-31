package com.epam.executors;

import com.epam.executors.model.Message;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


/**
 * #### This is a Client Prototype
 * <p>
 * Prerequisites:
 * - See at https://www.elastic.co/guide/en/kibana/current/tutorial-load-dataset.html (it is is resources an zipped_)
 * Task:
 * - Add Controller to provide service for shakespear.json.zip processing
 * - Add Job 1
 * -- Request with GET to server to download unzipped shakespear.json.zip from Server line by line in multiple threads
 * -- Collect lines and concatenate to receive json
 * - Add Job 2
 * -- Add a task to encode json in BASE64 format and send to the server (put encoded content to {@link com.epam.executors.model.Message} as a content)
 * -- Add matching search expression to find into {@link com.epam.executors.model.Message}
 * -- Server should accept it, decode and return occurences
 * - Add Job 3
 * -- When All Tasks are completed then Client should go down and report it in Console found lines number (integer)
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExecutorsApplicationTests {

    private static final String MATCH_EXPR = "Hello";

    @Test
    public void findMatchingLinesOccurences() throws URISyntaxException, InterruptedException {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = new URI("http://localhost:8080/app/custom");


        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//        ExecutorService executorService = Executors.newCachedThreadPool();

        List<Future> requests = new ArrayList();
        // sendRequestLine();

        // Go Down
    }

    private String sendRequestLine(RestTemplate restTemplate, URI uri, HttpEntity httpEntity, int line) {
//        ResponseEntity<Message> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Message.class);
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);
        final String lineStr = response.getBody();
        return lineStr;
    }

    private Integer sendRequestMatchesOccur(RestTemplate restTemplate, URI uri, HttpEntity httpEntity, int line) {
        ResponseEntity<Integer> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Integer.class);
//        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        final Integer occur = response.getBody();
        return occur;
    }
}
