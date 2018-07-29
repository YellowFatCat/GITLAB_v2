package com.epam.metrics.connectors.rest.jira.helpers;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpClientHelper {

    public static HttpClient buildHttpClient() {
        try {
            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
            connManager.setDefaultMaxPerRoute(20);
            connManager.setMaxTotal(40);

            return HttpClients.custom()
                    .setConnectionManager(connManager)
                    .build();
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
