package ru.otus.jdbc.mapper;

import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import lombok.val;

@SuppressWarnings("unchecked")
public class UniversalResultSetMapper<T> {
    private final EntityClassMetaData<T> entitySQLMetaData;

    public UniversalResultSetMapper(EntitySQLMetaData entitySQLMetaData) {
        val declaredFields = entitySQLMetaData.getClass().getDeclaredFields();
        EntityClassMetaData<T> first;
        try {
            val field = Arrays.stream(declaredFields)
                    .filter(a -> "entityClassMetaDataManager".equalsIgnoreCase(a.getName()))
                    .findFirst()
                    .orElseThrow();
            field.setAccessible(true);
            first = (EntityClassMetaData<T>) field.get(entitySQLMetaData);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        this.entitySQLMetaData = first;
    }

    public T map(ResultSet resultSet) {
        T tClass;
        try {
            tClass = entitySQLMetaData.getConstructor().newInstance();

            T finalTClass = tClass;
            entitySQLMetaData.getAllFields().forEach(field -> {
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
