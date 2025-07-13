package com.example.cinemahub2.configAndUtility;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class RolesConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            return null;
        }
        return String.join(",", roles); // Convert List<String> to a comma-separated string
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return Arrays.stream(dbData.split(",")).collect(Collectors.toList()); // Convert string back to List<String>
    }
}