package ru.javawebinar.topjava.web;


import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {

    private static final String TIME_PATTERN = "HH:mm";

    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    @Override
    public LocalTime convert(String source) {
        if (source.isEmpty()) {
            return null;
        }
        return LocalTime.parse(source, DATETIME_FORMATTER);
    }
}
