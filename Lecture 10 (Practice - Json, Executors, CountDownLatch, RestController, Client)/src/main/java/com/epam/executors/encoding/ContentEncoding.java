package com.epam.executors.encoding;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public enum ContentEncoding {
	RAW {
		public String toEncoding(final byte[] input) {
			return new String(input);
		}
	},
	BASE64 {
		public String toEncoding(final byte[] input) {
			return Hex.encodeHexString(Base64.encodeBase64(input));
		}
	}
}