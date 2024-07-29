package ru.javawebinar.topjava.util;


import org.springframework.core.convert.converter.Converter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class StringToLocalTimeConverter implements Converter<String, LocalTime> {

    private String timePattern = "HH:mm";

    private final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(timePattern);

    public String getTimePattern() {
        return timePattern;
    }

    public void setTimePattern(String timePattern) {
        this.timePattern = timePattern;
    }

    @Override
    public LocalTime convert(String source) {
        return LocalTime.parse(source, DATETIME_FORMATTER);
    }
}
