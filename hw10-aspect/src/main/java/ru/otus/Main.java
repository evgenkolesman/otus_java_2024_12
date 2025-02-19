package ru.otus;

import ru.otus.classrunner.ClassRunner;
import ru.otus.executable.Executable;
import ru.otus.executable.ExecutableImpl;

public class Main {

    public static void main(String[] args) {
        var proxifier = (Executable) ClassRunner.proxifier(new ExecutableImpl());

        System.out.println(proxifier.multiplicate(10, 2));
        System.out.println(proxifier.multiplicate(10, 2, 3));
        System.out.println(proxifier.multiplicate(10));
    }
}
