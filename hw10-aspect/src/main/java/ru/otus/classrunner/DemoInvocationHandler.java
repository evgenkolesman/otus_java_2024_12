package ru.otus.classrunner;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotation.Log;

class DemoInvocationHandler implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(DemoInvocationHandler.class);

    private final Object myClass;
    private final Class<?>[] interfaces;
    private final Set<Method> methodSet;

    DemoInvocationHandler(Object instance) {
        this.myClass = instance;
        this.methodSet = new HashSet<>();
        interfaces = myClass.getClass().getInterfaces();

        Arrays.stream(instance.getClass().getDeclaredMethods()).forEach(m -> {
            if (m.isAnnotationPresent(Log.class)) {
                Arrays.stream(interfaces)
                        .flatMap(interfacesClass -> Arrays.stream(interfacesClass.getDeclaredMethods()))
                        .filter(getMethodPredicate(m))
                        .findFirst()
                        .ifPresent(methodSet::add);
            }
        });
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (methodSet.contains(method)) {
            logger.info("invoking method:{}, arguments: {}", method, args);
        }
        return method.invoke(myClass, args);
    }

    public Class<?>[] interfaces() {
        return interfaces;
    }

    private Predicate<Method> getMethodPredicate(Method method) {
        return m -> Arrays.equals(m.getParameterTypes(), method.getParameterTypes())
                && m.getReturnType().equals(method.getReturnType());
    }

    @Override
    public String toString() {
        return "DemoInvocationHandler{" + "myClass=" + myClass + '}';
    }
}
