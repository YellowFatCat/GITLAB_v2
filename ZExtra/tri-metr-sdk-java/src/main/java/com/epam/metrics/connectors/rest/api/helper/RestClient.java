package com.epam.metrics.connectors.rest.api.helper;

import com.epam.metrics.connectors.rest.ICredentials;
import com.epam.metrics.connectors.rest.RestException;
import com.epam.metrics.connectors.rest.jira.helpers.JiraUtil;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import org.apache.http.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.springframework.http.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class RestClient {
    protected HttpClient httpClient = null;
    protected ICredentials creds = null;
    protected URI uri = null;

    protected ObjectMapper mapper = null;

    protected void setUpMapper() {
        mapper = new ObjectMapper();
//      mapper.registerModule(new JodaModule()
//                .addDeserializer(DateTime.class, new DateTimeDeserializer())
//                .addSerializer(DateTime.class, new DateTimeSerializer()));

        mapper.registerModule(new JodaModule()
                //.addDeserializer(DateTime.class, new DateTimeDeserializer())
        );
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setDateFormat(new ISO8601DateFormat());
    }

    /**
     * Exposes the http client.
     *
     * @return the httpClient property
     */
    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    /**
     * Creates a REST client instance with a URI.
     *
     * @param httpclient Underlying HTTP client to use
     * @param uri        Base URI of the remote REST service
     */
    public RestClient(HttpClient httpclient, URI uri) {
        this(httpclient, null, uri);
    }

    /**
     * Creates an authenticated REST client instance with a URI.
     *
     * @param httpclient Underlying HTTP client to use
     * @param creds      Credentials to send with each request
     * @param uri        Base URI of the remote REST service
     */
    public RestClient(HttpClient httpclient, ICredentials creds, URI uri) {
        this.httpClient = httpclient;
        this.creds = creds;
        this.uri = uri;
        setUpMapper();
    }

    /**
     * Build a URI from a path.
     *
     * @param path Path to append to the base URI
     * @return the full URI
     * @throws URISyntaxException when the path is invalid
     */
    public URI buildURI(String path) throws URISyntaxException {
        return buildURI(path, null);
    }

    /**
     * Build a URI from a path and query parmeters.
     *
     * @param path   Path to append to the base URI
     * @param params Map of key value pairs
     * @return the full URI
     * @throws URISyntaxException when the path is invalid
     */
    public URI buildURI(String path, Map<String, String> params) throws URISyntaxException {
        URIBuilder ub = new URIBuilder(uri);
        ub.setPath(ub.getPath() + path);

        if (params != null) {
            for (Map.Entry<String, String> ent : params.entrySet())
                ub.addParameter(ent.getKey(), ent.getValue());
        }

        return ub.build();
    }

    /// REQUEST

    protected <T> ResponseEntity<T> request(HttpRequestBase req, Class<T> clazz) throws IOException {

        req.addHeader("Accept", "application/json");

        if (creds != null)
            creds.authenticate(req);

        HttpResponse resp = httpClient.execute(req);

        if (HttpStatus.UNAUTHORIZED.value() == resp.getStatusLine().getStatusCode()) {
            throw new RuntimeException("Can not login with the credentials provided");
        }

        HttpEntity ent = resp.getEntity();
        StringBuilder result = new StringBuilder();

        JiraUtil.getResponse(resp, ent, result);

        // Do mapping from HTTP to Spring Response
        StatusLine sl = resp.getStatusLine();

//        if (sl.getStatusCode() >= 300)
//            throw new RestException(sl.getReasonPhrase(), sl.getStatusCode(), result.toString(), resp.getAllHeaders());
//
//        return result.length() > 0 ? mapper.readValue(result.toString(), clazz) : null;


        HttpHeaders headers = new HttpHeaders();
        for (Header header : resp.getAllHeaders()) {
            headers.add(header.getName(), header.getValue());
        }

        // Ignore sl.getReasonPhrase()

        if (result.length() > 0 && clazz != Void.class) {
            return ResponseEntity
                    .status(HttpStatus.valueOf(sl.getStatusCode()))
                    .headers(headers)
                    .body(mapper.readValue(result.toString(), clazz));
        } else {
            // there is not body
            return ResponseEntity
                    .status(HttpStatus.valueOf(sl.getStatusCode()))
                    .headers(headers)
                    .contentLength(0)
                    .build();
        }
    }

    protected <T> ResponseEntity<T> request(HttpEntityEnclosingRequestBase req, Object payload, Class<T> clazz)
            throws IOException {
        if (payload != null) {
            StringEntity ent = null;

            ent = new StringEntity(mapper.writeValueAsString(payload), "UTF-8");
            ent.setContentType("application/json");

            req.addHeader("Content-Type", "application/json");
            req.setEntity(ent);
        }

        return this.request(req, clazz);
    }

    /// GET

    public <T> ResponseEntity<T> get(URI uri, Class<T> clazz) throws IOException {
        return request(new HttpGet(uri), clazz);
    }

    public <T> ResponseEntity<T> get(String path, Map<String, String> params, Class<T> clazz) throws IOException, URISyntaxException {
        return get(buildURI(path, params), clazz);
    }

    public <T> ResponseEntity<T> get(String path, Class<T> clazz) throws IOException, URISyntaxException {
        return get(path, null, clazz);
    }

    /// PUT

    public <T> ResponseEntity<T> put(URI uri, Object payload, Class<T> clazz) throws IOException {
        return request(new HttpPut(uri), payload, clazz);
    }

    public <T> ResponseEntity<T> put(String path, Object payload, Class<T> clazz)
            throws IOException, URISyntaxException {
        return put(buildURI(path), payload, clazz);
    }

    /// POST

    public <T> ResponseEntity<T> post(URI uri, Object payload, Class<T> clazz) throws IOException {
        return request(new HttpPost(uri), payload, clazz);
    }

    public <T> ResponseEntity<T> post(String path, Object payload, Class<T> clazz)
            throws IOException, URISyntaxException {

        return post(buildURI(path), payload, clazz);
    }

    public <T> ResponseEntity<T> post(String path, Class<T> clazz)
            throws IOException, URISyntaxException {
        return post(buildURI(path), null, clazz);
    }

    ///DELETE

    public <T> ResponseEntity<T> delete(URI uri, Class<T> clazz) throws IOException {
        return request(new HttpDelete(uri), clazz);
    }

    public <T> ResponseEntity<T> delete(String path, Class<T> clazz) throws IOException, URISyntaxException {
        return delete(buildURI(path), clazz);
    }
}



