package ru.otus.custom_exceptions;

public class EvenException extends RuntimeException {
    public EvenException() {
        super("Exception in even second");
    }
}
