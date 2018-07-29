package com.epam.metrics.connectors.rest;

import com.epam.metrics.connectors.rest.jira.helpers.JiraRestClient;
import com.epam.metrics.connectors.rest.jira.helpers.JiraUtil;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class RestClient {
    protected HttpClient httpClient = null;
    protected ICredentials creds = null;
    protected URI uri = null;

    /**
     * Exposes the http client.
     *
     * @return the httpClient property
     */
    public HttpClient getHttpClient(){
        return this.httpClient;
    }

    /**
     * Creates a REST client instance with a URI.
     *
     * @param httpclient Underlying HTTP client to use
     * @param uri Base URI of the remote REST service
     */
    public RestClient(HttpClient httpclient, URI uri) {
        this(httpclient, null, uri);
    }

    /**
     * Creates an authenticated REST client instance with a URI.
     *
     * @param httpclient Underlying HTTP client to use
     * @param creds Credentials to send with each request
     * @param uri Base URI of the remote REST service
     */
    public RestClient(HttpClient httpclient, ICredentials creds, URI uri) {
        this.httpClient = httpclient;
        this.creds = creds;
        this.uri = uri;
    }

    /**
     * Build a URI from a path.
     *
     * @param path Path to append to the base URI
     *
     * @return the full URI
     *
     * @throws URISyntaxException when the path is invalid
     */
    public URI buildURI(String path) throws URISyntaxException {
        return buildURI(path, null);
    }

    /**
     * Build a URI from a path and query parmeters.
     *
     * @param path Path to append to the base URI
     * @param params Map of key value pairs
     *
     * @return the full URI
     *
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

    protected Object request(HttpRequestBase req) throws RestException, IOException {
        req.addHeader("Accept", "application/json");

        if (creds != null)
            creds.authenticate(req);

        HttpResponse resp = httpClient.execute(req);
        HttpEntity ent = resp.getEntity();
        StringBuilder result = new StringBuilder();

        JiraUtil.getResponse(resp, ent, result);

        StatusLine sl = resp.getStatusLine();

        if (sl.getStatusCode() >= 300)
            throw new RestException(sl.getReasonPhrase(), sl.getStatusCode(), result.toString(), resp.getAllHeaders());

        return result.toString();
    }

    protected Object request(HttpEntityEnclosingRequestBase req, String payload)
            throws RestException, IOException {
        if (payload != null) {
            StringEntity ent = null;

            ent = new StringEntity(payload, "UTF-8");
            ent.setContentType("application/json");

            req.addHeader("Content-Type", "application/json");
            req.setEntity(ent);
        }

        return this.request(req);
    }

    /**
     * Executes an HTTP DELETE with the given URI.
     *
     * @param uri Full URI of the remote host
     *
     * @return result of request
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     */
    public Object delete(URI uri) throws RestException, IOException {
        return request(new HttpDelete(uri));
    }

    /**
     * Executes an HTTP GET with the given URI.
     *
     * @param uri Full URI of the remote host
     *
     * @return result of request
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     */
    public Object get(URI uri) throws RestException, IOException {
        return request(new HttpGet(uri));
    }


}
