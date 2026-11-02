package com.educonnect.backend.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, String> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String convertToDatabaseColumn(LocalDateTime attribute) {
        if (attribute == null) return null;
        return attribute.format(FORMATTER);
    }

    @Override
    public LocalDateTime convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.trim().isEmpty()) return null;
        String s = dbData.trim();
        try {
            if (s.length() == 19) {
                return LocalDateTime.parse(s, FORMATTER);
            }
            if (s.length() > 19) {
                return LocalDateTime.parse(s.substring(0, 19), FORMATTER);
            }
            long epoch = Long.parseLong(s);
            return java.time.Instant.ofEpochMilli(epoch).atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
        } catch (Exception e) {
            return null;
        }
    }
}
