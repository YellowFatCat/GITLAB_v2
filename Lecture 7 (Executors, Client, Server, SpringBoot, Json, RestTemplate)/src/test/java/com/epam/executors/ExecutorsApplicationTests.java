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

        IntStream.range(0, 10).asLongStream().parallel().forEach((i) -> {
            Figure figure = generateFigure(name, (int) i);
            HttpEntity httpEntity = new HttpEntity(figure, headers);
            sendRequest(restTemplate, uri, httpEntity, (int) i);
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
////                    System.out.println("Execute task");
////                    sendRequest(restTemplate, uri, httpEntity, i);
//                }
//            });
            //requests.add(future);
        });

//        executorService.awaitTermination(10000, TimeUnit.MILLISECONDS);


        System.out.println("End HTTP requesting...");
        requests.forEach(future -> {
                    try {
                        System.out.println("Future: " + future.get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
        );

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
