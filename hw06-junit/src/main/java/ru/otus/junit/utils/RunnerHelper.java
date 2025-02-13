package ru.otus.junit.utils;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.*;
import ru.otus.junit.annotations.*;

public class RunnerHelper {
    public static Map<Class<? extends Annotation>, List<Method>> linkedMap = new LinkedHashMap<>();

    @SuppressWarnings({"varargs", "null"})
    public static void startTestMethods(Method methods, Class<?> aClass) {
        var ref = new Object() {
            Object testObject = null;
        };
        try {
            ref.testObject = aClass.getConstructor().newInstance();

            linkedMap.get(Before.class).forEach(m -> {
                try {
                    m.setAccessible(true);
                    m.invoke(ref.testObject);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
            try {
                methods.setAccessible(true);
                methods.invoke(ref.testObject);
            } finally {
                linkedMap.get(After.class).forEach(m -> {
                    try {
                        m.setAccessible(true);
                        m.invoke(ref.testObject);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void annotationCollector(Method[] methods) {
        linkedMap.put(Test.class, new ArrayList<>());
        linkedMap.put(Before.class, new ArrayList<>());
        linkedMap.put(After.class, new ArrayList<>());

        Arrays.stream(methods)
                .sequential()
                .forEach(m -> linkedMap.forEach((k, v) -> {
                    if (m.isAnnotationPresent(k)) {
                        linkedMap.get(k).add(m);
                    }
                }));
    }

    public static void testClass(String className) {

        Class<?> aClass = getAClass(className);
        Method[] methods = aClass.getDeclaredMethods();
        annotationCollector(methods);

        for (Method method : RunnerHelper.linkedMap.get(Test.class)) {
            RunnerHelper.startTestMethods(method, aClass);
        }
    }

    private static Class<?> getAClass(String className) {
        Class<?> aClass;
        try {
            aClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return aClass;
    }
}
