package ru.calculator;

public class Data {
    private final int value;

    public Data(Integer value) {
        if (value == null) throw new NullPointerException("VALUE CANNOT BE NULL");
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
