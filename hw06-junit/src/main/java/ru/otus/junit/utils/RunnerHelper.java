package ru.otus.junit.utils;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@UtilityClass
@Slf4j
public class RunnerHelper {
    public static Map<Class<? extends Annotation>, List<Method>> linkedMap = new java.util.LinkedHashMap<>();

    @SneakyThrows
    @SuppressWarnings({"varargs", "null"})
    public void startTestMethods(java.lang.reflect.Method methods, Class<?> aClass) {
        Object testObject = aClass.getConstructors()[0].newInstance((Object[]) null);

        linkedMap.get(ru.otus.junit.annotations.Before.class).forEach(m -> {
            try {
                m.setAccessible(true);
                m.invoke(testObject);
            } catch (IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
        try {
            methods.setAccessible(true);
            methods.invoke(testObject);
        } finally {
            linkedMap.get(ru.otus.junit.annotations.After.class).forEach(m -> {
                try {
                    m.setAccessible(true);
                    m.invoke(testObject);
                } catch (IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }
    public String pathReader() {
        try (var inputStream = new FileInputStream("./hw06-junit/src/main/resources/application.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);

            return properties.getProperty("test.location.package");
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
            throw new RuntimeException("Problems with property");
        }
    }

    public void annotationCollector(Method[] methods) {
        linkedMap.put(ru.otus.junit.annotations.Test.class, new java.util.ArrayList<>());
        linkedMap.put(ru.otus.junit.annotations.Before.class, new java.util.ArrayList<>());
        linkedMap.put(ru.otus.junit.annotations.After.class, new java.util.ArrayList<>());

        java.util.Arrays.stream(methods).sequential()
                .forEach(
                        m -> linkedMap.forEach(
                                (k, v) -> {
                                    if(m.isAnnotationPresent(k)) {
                                        linkedMap.get(k).add(m);
                                    }
                                }

                        )
                );

    }

    public void runExactMethod(String methodShortName, Class<?> aClass) {
        for (java.lang.reflect.Method method : ru.otus.junit.utils.RunnerHelper.linkedMap.get(ru.otus.junit.annotations.Test.class)) {
            if(method.getName().equals(methodShortName)) {
                ru.otus.junit.utils.RunnerHelper.startTestMethods(method, aClass);
            }
        }
    }


}
