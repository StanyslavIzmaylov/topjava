package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    private static String datePattern = "yyyy-MM-dd";

    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(datePattern);

    @Override
    public LocalDate convert(String source) {
        if (source.isEmpty() || source == null) {
            return null;
        }
        return LocalDate.parse(source, DATETIME_FORMATTER);
    }
}
