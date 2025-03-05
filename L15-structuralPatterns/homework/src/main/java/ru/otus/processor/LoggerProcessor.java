package ru.otus.processor;

import lombok.extern.slf4j.Slf4j;
import ru.otus.model.Message;

@Slf4j
public class LoggerProcessor implements Processor {

    private final Processor processor;

    public LoggerProcessor(Processor processor) {
        this.processor = processor;
    }

    @Override
    public Message process(Message message) {
        log.info("log processing message:{}", message);
        return processor.process(message);
    }
}
