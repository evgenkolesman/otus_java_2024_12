package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import lombok.experimental.UtilityClass;
import ru.otus.crm.annotation.Column;

@UtilityClass
public class FieldsUtil {

    public String toColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            return field.getAnnotation(Column.class).name();
        }
        return toSnakeCase(field.getName());
    }

    public String toSnakeCase(String camelCase) {
        if (camelCase == null || camelCase.isEmpty()) {
            return camelCase;
        }

        return camelCase.replaceAll("([a-z])([A-Z]+)", "$1_$2").toLowerCase();
    }
}
