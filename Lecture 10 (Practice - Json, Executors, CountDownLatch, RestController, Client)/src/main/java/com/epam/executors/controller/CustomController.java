package com.epam.executors.controller;

import com.epam.executors.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.io.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Base64;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@RestController
@RequestMapping(value = "/app")
public class CustomController {

    private String fileContent;
    private String[] lines;

    @PostConstruct
    public void init() throws URISyntaxException, IOException {

        URL url = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("shakespeare_6.0.json.zip_"));

        try (ZipFile zipFile = new ZipFile(new File(url.toURI()))) {

            ZipEntry entry = zipFile.getEntry("shakespeare_6.0.json");
            fileContent = IOUtils.toString(zipFile.getInputStream(entry));
            lines = fileContent.split("\n");
            for (int i = 0; i < lines.length; i++) {
                System.out.println(lines[i]);
            }

//            Enumeration<? extends ZipEntry> entries = zipFile.entries();
//            while (entries.hasMoreElements()) {
//                ZipEntry e = entries.nextElement();
//                String json = IOUtils.toString(zipFile.getInputStream(e));
//                System.out.println(e.getName());
////                System.out.println(json);
//                byte[] encoded = Base64.getEncoder().encode(json.getBytes());
//                System.out.println();
//                System.out.println(new String(encoded).substring(0, 100));
//            }
        }
    }


    @RequestMapping(value = "/getLine", method = RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
    public String readLine(@RequestParam(value = "line") final Integer line) {
        // TODO: return like lines[line], but take care of sync
        return lines[line];
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

        String[] decodedLines = new String(Base64.getDecoder().decode(message.getContent())).split("\n");
        for (String decodedLine : decodedLines) {
            if (decodedLine.contains(message.getMatchTemplate())) {
                foundMatches++;
            }
        }

        return String.valueOf(foundMatches);
    }
}
