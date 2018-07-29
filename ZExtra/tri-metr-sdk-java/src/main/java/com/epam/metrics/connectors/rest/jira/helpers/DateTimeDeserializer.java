package com.epam.metrics.connectors.rest.jira.helpers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.IOException;

/**
 * Created by Sergei_Zheleznov on 11.08.2017.
 */
public class DateTimeDeserializer extends StdScalarDeserializer<DateTime> {

    private static final Logger log = Logger.getLogger(DateTimeDeserializer.class);

    private final static DateTimeFormatter dtf = DateTimeFormat.forPattern("dd/MMM/YY hh:mm a");

    public DateTimeDeserializer() {
        super(DateTime.class);
    }

    private boolean isNone(String dateTimeString) {
        return dateTimeString.equals("None");
    }

    @Override
    public DateTime deserialize(JsonParser jsonParser,
                                DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

        JsonToken currentToken = jsonParser.getCurrentToken();
        if (currentToken == JsonToken.VALUE_STRING) {
            String dateTimeAsString = jsonParser.getText().trim();
            //return DateTime.parse(dateTimeAsString);


            try {
                DateTime jodatime = (!isNone(dateTimeAsString)) ? dtf.parseDateTime(dateTimeAsString) : null;
                return jodatime;
            } catch(IllegalArgumentException e) {
                log.error("Date cannot be parsed: '" + dateTimeAsString + "'", e);
                return null;
            }

            //Date date = new ISO8601DateFormat().parse(dateTimeAsString);
            //return new DateTime(date);
            //ISODateTimeFormat.basicDate().parseDateTime(dateTimeAsString);
            //return ISODateTimeFormat.basicDate().parseDateTime(dateTimeAsString);
        }
        throw new RuntimeException("Wrong DateTime JsonToken");
    }
}