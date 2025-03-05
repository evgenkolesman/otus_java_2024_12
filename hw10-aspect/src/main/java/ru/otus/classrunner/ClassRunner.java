package ru.otus.classrunner;

import java.lang.reflect.Proxy;

public class ClassRunner {

    /**
     * Instance with no args constructor
     *
     * @param instance Object
     * @return proxy
     */
    public static Object proxifier(Object instance) {

        final DemoInvocationHandler handler = new DemoInvocationHandler(instance);
        return Proxy.newProxyInstance(ClassRunner.class.getClassLoader(), handler.interfaces(), handler);
    }
}
