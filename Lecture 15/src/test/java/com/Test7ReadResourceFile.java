package com;


import org.junit.Test;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertEquals;

public class Test7ReadResourceFile {

    private static String expectedContent = "UTF-8 format"; // default representation in UTF-8

    @Test
    public void testReadFile() throws InterruptedException {

        System.out.println("Unicode Value for '" + expectedContent + "' = " + Integer.toHexString(expectedContent.codePointAt(0)));
        byte[] bytes = expectedContent.getBytes();
        System.out.println("The UTF-8 Character = '" + expectedContent + "'  | Default: Number of Bytes=" + bytes.length);
        String stringUTF16 = new String(bytes, StandardCharsets.UTF_16);
        System.out.println("The corresponding UTF-16 Character=" + stringUTF16 + "  | UTF-16: Number of Bytes=" + stringUTF16.getBytes().length);
        System.out.println("----------------------------------------------------------------------------------------");

        // TODO: Read from file in resources directory
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("file.txt");

        String fileContent = readContentInUTF16(is);
        System.out.println("Loaded Content in UTF-16: " + fileContent);

        assertEquals(stringUTF16, fileContent);
    }

    // TODO: InputStream gives bytes without Char Set. Specify it.
    private String readContentInUTF16(InputStream is) {
        // TODO: read from IO stream and convert to String UTF-16 if original format is 8 bytes based
        return null;
    }
}
