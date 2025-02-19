package ru.otus.classrunner;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.otus.annotation.Log;

public class ClassRunner {

    static final List<Method> list = new ArrayList<>();

    /**
     * Instance with no args constructor
     *
     * @param instance Object
     * @return proxy
     */
    public static Object proxifier(Object instance) {
        try {
            Arrays.stream(instance.getClass().getDeclaredMethods()).forEach(m -> {
                if (m.isAnnotationPresent(Log.class)) {
                    list.add(m);
                }
            });

            final InvocationHandler handler = new DemoInvocationHandler(instance, new ArrayList<>(list));
            final Class<?>[] interfaces = instance.getClass().getInterfaces();

            return Proxy.newProxyInstance(ClassRunner.class.getClassLoader(), interfaces, handler);
        } finally {
            list.clear();
        }
    }
}
