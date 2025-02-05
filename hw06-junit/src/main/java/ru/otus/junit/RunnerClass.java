package ru.otus.junit;

import lombok.extern.slf4j.*;
import ru.otus.junit.utils.*;

import java.lang.reflect.*;
import java.util.*;


@Slf4j
public class RunnerClass {
 //    TestClass.firstTest TestClass.secondTest
    public static void main(String[] args) throws ClassNotFoundException {
        System.out.println("Print testclass.method  which need to run");
        Scanner scanner = new Scanner(System.in);
        String classAndMethod = scanner.nextLine();

        checkNotBlank(classAndMethod);
        String classShortName = classAndMethod.split("\\.")[0].trim();
        checkNotBlank(classAndMethod);
        String methodShortName = classAndMethod.split("\\.")[1].trim();
        checkNotBlank(methodShortName);

        Class<?> aClass = Class.forName("%s.%s".formatted(RunnerHelper.pathReader(), classShortName));

        Method[] methods = aClass.getDeclaredMethods();
        RunnerHelper.annotationCollector(methods);
        RunnerHelper.runExactMethod(methodShortName, aClass);

    }

    private static void checkNotBlank(String classAndMethod) {
        if (classAndMethod == null || classAndMethod.isBlank()) {
            throw new RuntimeException("WRONG testclass.method  must not be blank");
        }
    }
}
