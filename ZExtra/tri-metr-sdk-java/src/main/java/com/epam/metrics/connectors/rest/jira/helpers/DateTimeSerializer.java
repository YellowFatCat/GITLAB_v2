package com.epam.metrics.connectors.rest.jira.helpers;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdScalarSerializer;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import java.io.IOException;

/**
 * Created by Sergei_Zheleznov on 11.08.2017.
 */
public class DateTimeSerializer extends StdScalarSerializer<DateTime> {

    public DateTimeSerializer() {
        super(DateTime.class);
    }

    @Override
    public void serialize(DateTime dateTime,
                          JsonGenerator jsonGenerator,
                          SerializerProvider provider) throws IOException, JsonGenerationException {
        String dateTimeAsString = ISODateTimeFormat.basicDateTime().print(dateTime);
        jsonGenerator.writeString(dateTimeAsString);
    }
}