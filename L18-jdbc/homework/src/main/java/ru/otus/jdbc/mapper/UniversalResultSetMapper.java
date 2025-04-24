package ru.otus.jdbc.mapper;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import lombok.val;

public class UniversalResultSetMapper<T> {
    private final EntityClassMetaData<T> entityClassMetaData;

    public UniversalResultSetMapper(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    public T map(ResultSet resultSet) {
        T tClass;
        try {
            tClass = entityClassMetaData.getConstructor().newInstance();

            T finalTClass = tClass;
            entityClassMetaData.getAllFields().forEach(field -> {
                try {
                    if (resultSet.next()) {
                        field.setAccessible(true);
                        val declaringClass = field.getType();
                        val object =
                                checkNullField(resultSet.getObject(FieldsUtil.toColumnName(field), declaringClass));
                        field.set(finalTClass, object);
                    }
                } catch (SQLException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            });
            return tClass;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Object checkNullField(Object object) {
        if (object instanceof String && "null".equalsIgnoreCase(((String) object).trim())) {
            return null;
        } else return object;
    }
}
