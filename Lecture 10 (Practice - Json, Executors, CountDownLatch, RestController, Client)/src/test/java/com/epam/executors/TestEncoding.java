package com.epam.executors;

import org.apache.commons.codec.DecoderException;
import org.junit.Test;
import sun.misc.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.epam.executors.encoding.ContentEncoding.BASE64;

public class TestEncoding {

    @Test
    public void testEncoding() throws IOException, DecoderException {
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("file.png");
        byte file[] = IOUtils.readFully(is, 1000, true);

        String str = BASE64.toEncoding("Transform me to BASE64 Format".getBytes());
        System.out.println("BASE64 format: " + str);


        String fileBASE64 = BASE64.toEncoding(file);
        System.out.println("File in BASE64: " + fileBASE64);


        String decodedStr = new String(BASE64.toDecoding(str), "UTF-8");
        System.out.println("Decoded 64: " + decodedStr);

    }
}
