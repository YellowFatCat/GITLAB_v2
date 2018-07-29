package com.epam.metrics.connectors.rest.jira.helpers;

import com.epam.metrics.connectors.rest.ICredentials;
import com.epam.metrics.connectors.rest.RestException;
import com.epam.metrics.connectors.rest.api.helper.RestClient;
import com.epam.metrics.connectors.rest.jira.models.oldModels.Issue;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;


import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Derived from rcars jira-client
 */
public class JiraRestClient extends RestClient {

    protected void setUpMapper() {
        mapper = new ObjectMapper();
//      mapper.registerModule(new JodaModule()
//                .addDeserializer(DateTime.class, new DateTimeDeserializer())
//                .addSerializer(DateTime.class, new DateTimeSerializer()));

        mapper.registerModule(new JodaModule()
                //.addDeserializer(DateTime.class, new DateTimeDeserializer())
        );
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //mapper.setDateFormat(new ISO8601DateFormat());
    }

    /**
     * Creates a REST client instance with a URI.
     *
     * @param httpclient Underlying HTTP client to use
     * @param uri Base URI of the remote REST service
     */
    public JiraRestClient(HttpClient httpclient, URI uri) {
        this(httpclient, null, uri);
    }

    /**
     * Creates an authenticated REST client instance with a URI.
     *
     * @param httpclient Underlying HTTP client to use
     * @param creds Credentials to send with each request
     * @param uri Base URI of the remote REST service
     */
    public JiraRestClient(HttpClient httpclient, ICredentials creds, URI uri) {
        super(httpclient, creds, uri);
    }


    /* #################################################################################################################
       Many of its methods return a raw JSON Object while we should design the class to work with prototypes and classes
       #################################################################################################################
    */

    // TODO: deprecate it
    protected JSON request(HttpRequestBase req) throws RestException, IOException {
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

        return result.length() > 0 ? JSONSerializer.toJSON(result.toString()): null;
    }


    // TODO: deprecate it
    protected JSON request(HttpEntityEnclosingRequestBase req, String payload)
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

    // TODO: deprecate it
    private JSON request(HttpEntityEnclosingRequestBase req, File file)
            throws RestException, IOException {
        if (file != null) {
            File fileUpload = file;
            req.setHeader("X-Atlassian-Token", "nocheck");
            MultipartEntity ent = new MultipartEntity();
            ent.addPart("file", new FileBody(fileUpload));
            req.setEntity(ent);
        }
        return request(req);
    }

    // TODO: deprecate it
    private JSON request(HttpEntityEnclosingRequestBase req, Issue.NewAttachment... attachments)
            throws RestException, IOException {
        if (attachments != null) {
            req.setHeader("X-Atlassian-Token", "nocheck");
            MultipartEntity ent = new MultipartEntity();
            //FIXME:attachments
//            for(Issue.NewAttachment attachment : attachments) {
//                String filename = attachment.getFilename();
//                Object content = attachment.getContent();
//                if (content instanceof byte[]) {
//                    ent.addPart("file", new ByteArrayBody((byte[]) content, filename));
//                } else if (content instanceof InputStream) {
//                    ent.addPart("file", new InputStreamBody((InputStream) content, filename));
//                } else if (content instanceof File) {
//                    ent.addPart("file", new FileBody((File) content, filename));
//                } else if (content == null) {
//                    throw new IllegalArgumentException("Missing content for the file " + filename);
//                } else {
//                    throw new IllegalArgumentException(
//                            "Expected file type byte[], java.io.InputStream or java.io.File but provided " +
//                                    content.getClass().getName() + " for the file " + filename);
//                }
//            }
            req.setEntity(ent);
        }
        return request(req);
    }

    // TODO: deprecate it
    private JSON request(HttpEntityEnclosingRequestBase req, JSON payload)
            throws RestException, IOException {

        return request(req, payload != null ? payload.toString() : null);
    }

    // TODO: deprecate it
    /**
     * Executes an HTTP DELETE with the given URI.
     *
     * @param uri Full URI of the remote host
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     */
    public JSON delete(URI uri) throws RestException, IOException {
        return request(new HttpDelete(uri));
    }

    // TODO: deprecate it
    /**
     * Executes an HTTP DELETE with the given path.
     *
     * @param path Path to be appended to the URI supplied in the construtor
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     * @throws URISyntaxException when an error occurred appending the path to the URI
     */
    public JSON delete(String path) throws RestException, IOException, URISyntaxException {
        return delete(buildURI(path));
    }

    // TODO: deprecate it
    /**
     * Executes an HTTP GET with the given URI.
     *
     * @param uri Full URI of the remote host
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     */
    public JSON get(URI uri) throws RestException, IOException {
        return request(new HttpGet(uri));
    }


    // TODO: deprecate it
    /**
     * Executes an HTTP GET with the given path.
     *
     * @param path Path to be appended to the URI supplied in the construtor
     * @param params Map of key value pairs
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     * @throws URISyntaxException when an error occurred appending the path to the URI
     */
    public JSON get(String path, Map<String, String> params) throws RestException, IOException, URISyntaxException {
        return get(buildURI(path, params));
    }

    // TODO: deprecate it
    /**
     * Executes an HTTP GET with the given path.
     *
     * @param path Path to be appended to the URI supplied in the construtor
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     * @throws URISyntaxException when an error occurred appending the path to the URI
     */
//    public JSON get(String path) throws RestException, IOException, URISyntaxException {
//        return get(path, null);
//    }

    // TODO: deprecate it
    /**
     * Executes an HTTP POST with the given URI and payload.
     *
     * @param uri Full URI of the remote host
     * @param payload JSON-encoded data to send to the remote service
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     */
    public JSON post(URI uri, JSON payload) throws RestException, IOException {
        return request(new HttpPost(uri), payload);
    }

    // TODO: deprecate it
    /**
     * Executes an HTTP POST with the given URI and payload.
     *
     * At least one JIRA REST host expects malformed JSON. The payload
     * argument is quoted and sent to the server with the application/json
     * Content-Type header. You should not use this function when proper JSON
     * is expected.
     *
     * @see https://jira.atlassian.com/browse/JRA-29304
     *
     * @param uri Full URI of the remote host
     * @param payload Raw string to send to the remote service
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     */
    public JSON post(URI uri, String payload) throws RestException, IOException {
        String quoted = null;
        if(payload != null && !payload.equals(new JSONObject())){
            quoted = String.format("\"%s\"", payload);
        }
        return request(new HttpPost(uri), quoted);
    }

    // TODO: deprecate it
    /**
     * Executes an HTTP POST with the given path and payload.
     *
     * @param path Path to be appended to the URI supplied in the construtor
     * @param payload JSON-encoded data to send to the remote service
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     * @throws URISyntaxException when an error occurred appending the path to the URI
     */
    public JSON post(String path, JSON payload)
            throws RestException, IOException, URISyntaxException {

        return post(buildURI(path), payload);
    }

    // TODO: deprecate it
    /**
     * Executes an HTTP POST with the given path.
     *
     * @param path Path to be appended to the URI supplied in the construtor
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     * @throws URISyntaxException when an error occurred appending the path to the URI
     */
    public JSON post(String path)
            throws RestException, IOException, URISyntaxException {

        return post(buildURI(path), new JSONObject());
    }


    // TODO: deprecate it
    /**
     * Executes an HTTP POST with the given path and file payload.
     *
     * @param path Full URI of the remote host
     * @param file java.io.File
     *
     * @throws URISyntaxException
     * @throws IOException
     * @throws RestException
     */
    public JSON post(String path, File file) throws RestException, IOException, URISyntaxException{
        return request(new HttpPost(buildURI(path)), file);
    }

    // TODO: deprecate it
    /**
     * Executes an HTTP POST with the given path and file payloads.
     *
     * @param path    Full URI of the remote host
     * @param attachments   the name of the attachment
     *
     * @throws URISyntaxException
     * @throws IOException
     * @throws RestException
     */
    public JSON post(String path, Issue.NewAttachment... attachments)
            throws RestException, IOException, URISyntaxException
    {
        return request(new HttpPost(buildURI(path)), attachments);
    }

    // TODO: deprecate it
    /**
     * Executes an HTTP PUT with the given URI and payload.
     *
     * @param uri Full URI of the remote host
     * @param payload JSON-encoded data to send to the remote service
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     */
    public JSON put(URI uri, JSON payload) throws RestException, IOException {
        return request(new HttpPut(uri), payload);
    }

    // TODO: deprecate it
    /**
     * Executes an HTTP PUT with the given path and payload.
     *
     * @param path Path to be appended to the URI supplied in the construtor
     * @param payload JSON-encoded data to send to the remote service
     *
     * @return JSON-encoded result or null when there's no content returned
     *
     * @throws RestException when an HTTP-level error occurs
     * @throws IOException when an error reading the response occurs
     * @throws URISyntaxException when an error occurred appending the path to the URI
     */
    public JSON put(String path, JSON payload)
            throws RestException, IOException, URISyntaxException {

        return put(buildURI(path), payload);
    }
}

