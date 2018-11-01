package com.epam.executors.controller;

import com.epam.executors.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping(value = "/app")
public class CustomController {

    private static String fileContent; // not static?
    private static String[] lines;

    // TODO:
    // Get stream, unzip the file
    // Thread.currentThread().getContextClassLoader().getResourceAsStream("<path to shakespeare.json.zip_>");

//    static { // not static???
//        final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("<path to shakespeare.json.zip_>");
//        try {
//            // TODO: use java.util.zip.ZipFile
//            // Wrap it with ZipFile. Search on the internet example
//
//            fileContent = IOUtils.toString(is); // CharSet??? UTF-16 or UTF-8
//            lines = fileContent.split("\n"); //?? what is delimiter
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @RequestMapping(value = "/getLine", method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public String readLine( @RequestParam(value = "line") final Integer line) {
        String lineStr = "Italy";
        // TODO: return like lines[line], but take care of reentrantlock
        return lineStr;
    }

    /**
     * TODO:
     * The Enhanced Task: implement search service
     *  - try to use {@link com.epam.executors.encoding.ContentEncoding} to decode
     * @param message
     * @return
     * @throws InterruptedException
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String findMatches(@RequestBody Message message) throws InterruptedException, JsonProcessingException {
        int foundMatches = 0;

        // TODO:
        // Decode message content
        // Get Message content and Parse into Lines with deliminator : "\n" ???
        // Find matches in every line and count them

        return String.valueOf(foundMatches);
    }
}
