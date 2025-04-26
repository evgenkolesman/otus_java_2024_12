package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.val;
import ru.otus.crm.annotation.Creator;
import ru.otus.crm.annotation.Id;
import ru.otus.crm.annotation.Table;

public abstract class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> clazz;
    private final List<Field> listFieldsCache;
    private final Constructor<T> constructor;

    @SuppressWarnings("unchecked")
    public EntityClassMetaDataImpl() {
        Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof ParameterizedType) {
            this.clazz = (Class<T>) ((ParameterizedType) superClass).getActualTypeArguments()[0];
        } else {
            throw new RuntimeException("Unable to determine generic type parameter");
        }
        this.listFieldsCache = Arrays.stream(clazz.getDeclaredFields()).toList();
        this.constructor = (Constructor<T>) Arrays.stream(clazz.getConstructors())
                .filter(constructor -> constructor.isAnnotationPresent(Creator.class))
                .findFirst()
                .orElse(clazz.getConstructors()[0]);
    }

    @Override
    public String getName() {
        val tableName = Arrays.stream(clazz.getAnnotations())
                .filter(annotation -> annotation.annotationType().equals(Table.class))
                .findFirst();
        if (tableName.isPresent()) {
            return ((Table) tableName.get()).name();
        }
        return clazz.getSimpleName().toLowerCase();
    }

    @Override
    public Constructor<T> getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() {
        List<Field> allIdFields = getAllFields();
        val list = listFieldsCache.stream()
                .filter(a -> a.isAnnotationPresent(Id.class))
                .toList();

        if (list.isEmpty()) {
            allIdFields = listFieldsCache.stream()
                    .filter(a -> "id".equalsIgnoreCase(a.getName()))
                    .toList();
        }
        return allIdFields.isEmpty() ? null : allIdFields.getFirst();
    }

    @Override
    public List<Field> getAllFields() {
        return listFieldsCache == null ? Collections.emptyList() : listFieldsCache;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return listFieldsCache.stream()
                .filter(a -> !a.isAnnotationPresent(Id.class) && !"id".equalsIgnoreCase(a.getName()))
                .toList();
    }
}
