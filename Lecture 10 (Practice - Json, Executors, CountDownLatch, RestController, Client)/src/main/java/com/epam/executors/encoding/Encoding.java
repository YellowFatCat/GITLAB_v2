package com.epam.executors.encoding;

import org.apache.commons.codec.DecoderException;

public interface Encoding {
    public String toEncoding(final byte[] input);
    public byte[] toDecoding(final String input) throws DecoderException;
}
