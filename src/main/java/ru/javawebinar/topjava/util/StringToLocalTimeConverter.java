package ru.javawebinar.topjava.util;


import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {

    private static String timePattern = "HH:mm";

    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(timePattern);

    @Override
    public LocalTime convert(String source) {
        if (source.isEmpty() || source == null) {
            return null;
        }
        return LocalTime.parse(source, DATETIME_FORMATTER);
    }
}
