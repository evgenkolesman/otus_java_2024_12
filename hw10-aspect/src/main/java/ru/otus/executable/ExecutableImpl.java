package ru.otus.executable;

import ru.otus.annotation.Log;

public class ExecutableImpl implements Executable {

    @Override
    public int multiplicate(int value) {
        return value * value;
    }

    @Override
    public int multiplicate(int value, int secondValue) {
        return value * secondValue;
    }

    @Log
    @Override
    public int multiplicate(int value, int secondValue, int thirdValue) {
        return value * secondValue * thirdValue;
    }
}
