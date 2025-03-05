package ru.otus.custom_exceptions;

public class ReCheckIdException extends RuntimeException {
    public ReCheckIdException() {
        super("RECHECK ID");
    }
}
