package ru.otus.jdbc.mapper;

import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {
    private final EntityClassMetaData<T> entityClassMetaDataManager;
    private final String DEFAULT_SCHEMA;

    @Override
    public String getSelectAllSql() {
        return "SELECT *  FROM %s.%s".formatted(DEFAULT_SCHEMA, entityClassMetaDataManager.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return getSelectAllSql() + " WHERE id = ?";
    }

    @Override
    public String getInsertSql() {
        val fieldsWithoutId = entityClassMetaDataManager.getFieldsWithoutId().stream()
                .map(FieldsUtil::toColumnName)
                .collect(Collectors.joining(", "));
        val fieldsWithoutIdForInsert = entityClassMetaDataManager.getFieldsWithoutId().stream()
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        return "INSERT INTO %s(%s) VALUES (%s)"
                .formatted(
                        //                        DEFAULT_SCHEMA,
                        DEFAULT_SCHEMA + "." + entityClassMetaDataManager.getName(),
                        fieldsWithoutId,
                        fieldsWithoutIdForInsert);
    }

    @Override
    public String getUpdateSql() {
        val fieldsWithoutId = entityClassMetaDataManager.getFieldsWithoutId().stream()
                .map(field -> "%s = :%s".formatted(FieldsUtil.toColumnName(field), FieldsUtil.toColumnName(field)))
                .collect(Collectors.joining(", "));

        return "UPDATE %s,%s SET %s WHERE %s = ?"
                .formatted(
                        DEFAULT_SCHEMA,
                        entityClassMetaDataManager.getName(),
                        fieldsWithoutId,
                        FieldsUtil.toColumnName(entityClassMetaDataManager.getIdField()));
    }
}
