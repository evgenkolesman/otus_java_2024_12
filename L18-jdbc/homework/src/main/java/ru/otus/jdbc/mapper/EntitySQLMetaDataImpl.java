package ru.otus.jdbc.mapper;

import java.util.stream.Collectors;
import lombok.Getter;
import lombok.val;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData<T> {
    @Getter
    private final EntityClassMetaData<T> entityClassMetaData;

    private final String resourceSchema;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        this.resourceSchema = getSchemaFromResources();
    }

    private String getSchemaFromResources() {
        try {
            return System.getenv().get("db.schema");
        } catch (Exception e) {
            return "public";
        }
    }

    @Override
    public String getSelectAllSql() {
        return "SELECT *  FROM %s.%s".formatted(resourceSchema, entityClassMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return getSelectAllSql() + " WHERE id = ?";
    }

    @Override
    public String getInsertSql() {
        val fieldsWithoutId = entityClassMetaData.getFieldsWithoutId().stream()
                .map(FieldsUtil::toColumnName)
                .collect(Collectors.joining(", "));
        val fieldsWithoutIdForInsert = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        return "INSERT INTO %s(%s) VALUES (%s)"
                .formatted(
                        //                        DEFAULT_SCHEMA,
                        resourceSchema + "." + entityClassMetaData.getName(),
                        fieldsWithoutId,
                        fieldsWithoutIdForInsert);
    }

    @Override
    public String getUpdateSql() {
        val fieldsWithoutId = entityClassMetaData.getFieldsWithoutId().stream()
                .map(field -> "%s = :%s".formatted(FieldsUtil.toColumnName(field), FieldsUtil.toColumnName(field)))
                .collect(Collectors.joining(", "));

        return "UPDATE %s,%s SET %s WHERE %s = ?"
                .formatted(
                        resourceSchema,
                        entityClassMetaData.getName(),
                        fieldsWithoutId,
                        FieldsUtil.toColumnName(entityClassMetaData.getIdField()));
    }
}
