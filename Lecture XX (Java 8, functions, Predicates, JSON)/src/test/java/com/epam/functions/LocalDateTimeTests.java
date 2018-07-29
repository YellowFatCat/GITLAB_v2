package com.epam.functions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.OptionalInt;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
public class LocalDateTimeTests {

    private static final DateTimeFormatter formatter_iso = DateTimeFormatter.ISO_DATE;
    private static final DateTimeFormatter formatter_iso_date_time = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    @Test
    public void collectors() throws Throwable {
        LocalDate localDate = LocalDate.now();
        System.out.println("Local Date: " + localDate.format(formatter_iso));

        System.out.println("Local Date time: " + localDate);
        System.out.println("Local Date time: " + localDate.format(formatter_iso_date_time));
//        System.out.println("Local Date time: " + localDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
    }


    @Test
    public void findFiest(){
        IntStream stream = IntStream.rangeClosed(0, 9);
        OptionalInt result = stream.parallel().filter(i -> i < 5 && i > 0).findFirst();
        System.out.println("result: " + result.getAsInt());
    }

}





