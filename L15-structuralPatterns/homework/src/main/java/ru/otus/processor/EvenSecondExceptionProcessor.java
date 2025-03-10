package ru.otus.processor;

import lombok.RequiredArgsConstructor;
import ru.otus.custom_exceptions.EvenException;
import ru.otus.model.Message;

@RequiredArgsConstructor
public class EvenSecondExceptionProcessor implements Processor {
    private final DateTimeProvider dateTimeProvider;

    @Override
    public Message process(Message message) {
        long currentSecond = dateTimeProvider.getDate().getEpochSecond();
        if (currentSecond % 2 == 0) {
            throw new EvenException();
        }
        return message;
    }
}
