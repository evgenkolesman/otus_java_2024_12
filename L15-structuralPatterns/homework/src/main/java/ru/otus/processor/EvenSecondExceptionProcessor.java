package ru.otus.processor;

import java.time.Instant;
import ru.otus.custom_exceptions.EvenException;
import ru.otus.model.Message;

public class EvenSecondExceptionProcessor implements Processor {
    @Override
    public Message process(Message message) {
        long currentSecond = Instant.now().getEpochSecond();
        if (currentSecond % 2 == 0) {
            throw new EvenException();
        }
        return message;
    }
}
