package ru.javawebinar.topjava.util;

import org.springframework.core.convert.converter.Converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StringToLocalDateConverter implements Converter<String, LocalDate> {

    private String datePattern = "yyyy-MM-dd";

    private final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(datePattern);

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    @Override
    public LocalDate convert(String source) {
        return LocalDate.parse(source, DATETIME_FORMATTER);
    }
}
