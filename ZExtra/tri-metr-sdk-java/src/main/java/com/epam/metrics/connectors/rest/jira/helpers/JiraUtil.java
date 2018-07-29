package com.epam.metrics.connectors.rest.jira.helpers;

import com.epam.metrics.connectors.rest.ResponseStatusException;
import org.apache.http.*;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.http.*;
import org.springframework.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by Sergei_Zheleznov on 18.08.2017.
 */
public final class JiraUtil {
    private JiraUtil(){}

    public static void getResponse(HttpResponse resp, HttpEntity ent, StringBuilder result) throws IOException {
        if (ent != null) {
            String encoding = null;
            if (ent.getContentEncoding() != null) {
                encoding = ent.getContentEncoding().getValue();
            }

            Header contentTypeHeader = resp.getFirstHeader("Content-Type");
            if (encoding == null && contentTypeHeader != null) {
                HeaderElement[] contentTypeElements = contentTypeHeader.getElements();
                for (HeaderElement he : contentTypeElements) {
                    NameValuePair nvp = he.getParameterByName("charset");
                    if (nvp != null) {
                        encoding = nvp.getValue();
                    }
                }
            }

            InputStreamReader isr = encoding != null ?
                    new InputStreamReader(ent.getContent(), encoding) :
                    new InputStreamReader(ent.getContent());
            BufferedReader br = new BufferedReader(isr);
            String line;

            while ((line = br.readLine()) != null) {
                result.append(line);
            }

            isr.close();
            br.close();

            EntityUtils.consumeQuietly(ent);
        }
    }

    public static <T> T validateResponse(ResponseEntity<T> response, HttpStatus... status) {
        //TODO: Add logging of invalid code
        if (!Arrays.asList(asArray(status)).contains(response.getStatusCode())) {
            // log
            throw new ResponseStatusException(response);
        }
        return response.getBody();
    }

    public static HttpStatus[] asArray(HttpStatus... ints) {
        return ints;
    }
}
