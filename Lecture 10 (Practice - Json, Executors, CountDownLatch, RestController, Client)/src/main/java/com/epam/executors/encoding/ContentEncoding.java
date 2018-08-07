package com.epam.executors.encoding;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public enum ContentEncoding implements Encoding {
    RAW {
        public String toEncoding(final byte[] input) {
            return new String(input);
        }

        public byte[] toDecoding(final String input) {
            return new String(input).getBytes();
        }
    },
    BASE64 {
        public String toEncoding(final byte[] input) {
            return Hex.encodeHexString(Base64.encodeBase64(input));
        }

        public byte[] toDecoding(final String input) throws DecoderException {
            byte[] bytes = Base64.decodeBase64(input);
            return bytes;
        }
    }
}