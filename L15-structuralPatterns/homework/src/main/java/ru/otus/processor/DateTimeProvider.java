package ru.otus.processor;

import java.time.Instant;

public interface DateTimeProvider {
    Instant getDate();
}
