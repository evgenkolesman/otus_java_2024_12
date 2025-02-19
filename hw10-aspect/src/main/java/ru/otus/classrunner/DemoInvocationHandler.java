package ru.otus.classrunner;

import java.lang.reflect.*;
import java.util.*;
import java.util.function.*;
import org.slf4j.*;

class DemoInvocationHandler implements InvocationHandler {
    private static final Logger logger = LoggerFactory.getLogger(DemoInvocationHandler.class);

    private final Object myClass;
    private final List<Method> methodList;

    DemoInvocationHandler(Object myClass, List<Method> methodList) {
        this.myClass = myClass;
        this.methodList = methodList;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (methodList.stream()
                .filter(m -> m.getName().equals(method.getName()))
                .anyMatch(getMethodPredicate(method, args))) {
            logger.info("invoking method:{}, arguments: {}", method, args);
        }

        return method.invoke(myClass, args);
    }

    private Predicate<Method> getMethodPredicate(Method method, Object[] args) {
        return m -> m.getParameterCount() == args.length
                && Arrays.equals(m.getParameterTypes(), method.getParameterTypes())
                && m.getReturnType().equals(method.getReturnType());
    }

    @Override
    public String toString() {
        return "DemoInvocationHandler{" + "myClass=" + myClass + '}';
    }
}
