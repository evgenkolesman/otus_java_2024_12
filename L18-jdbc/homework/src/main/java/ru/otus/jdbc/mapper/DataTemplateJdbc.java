package ru.otus.jdbc.mapper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.*;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;
import ru.otus.crm.annotation.Id;

/** Сохратяет объект в базу, читает объект из базы */
@SuppressWarnings("java:S1068")
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {

        return dbExecutor.executeSelect(
                connection,
                entitySQLMetaData.getSelectByIdSql(),
                List.of(id),
                (ResultSet resultSet) -> new UniversalResultSetMapper<T>(entitySQLMetaData).map(resultSet));
    }

    @Override
    public List<T> findAll(Connection connection) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long insert(Connection connection, T client) {
        val collect = getFieldsMap(client);
        val values = new ArrayList<>(collect.values());
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), values);
    }

    private static <T> Map<String, Object> getFieldsMap(T client) {
        var fieldsMap = new HashMap<String, Object>();
        Arrays.stream(client.getClass().getDeclaredFields())
                .filter(a -> !a.isAnnotationPresent(Id.class) && !"id".equalsIgnoreCase(a.getName()))
                .forEach(field -> {
                    try {
                        field.setAccessible(true);
                        fieldsMap.put(field.getName(), field.get(client));
                    } catch (IllegalAccessException e) {
                        fieldsMap.put(field.getName(), null);
                    }
                });
        return fieldsMap;
    }

    @Override
    public void update(Connection connection, T client) {
        throw new UnsupportedOperationException();
    }
}
