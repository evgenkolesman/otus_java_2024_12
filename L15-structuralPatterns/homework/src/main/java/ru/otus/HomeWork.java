package ru.otus;

import java.util.List;
import java.util.function.Consumer;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.model.ObjectForMessage;
import ru.otus.processor.EvenSecondExceptionProcessor;
import ru.otus.processor.Processor;
import ru.otus.processor.SwapFieldsProcessor;

@Slf4j
public class HomeWork {

    public static void main(String[] args) {
        val field13 = new ObjectForMessage();
        field13.setData(List.of("field13"));
        Message message = Message.builder()
                .field1("field1")
                .field2("field2")
                .field3("field3")
                .field11("field11")
                .field12("field12")
                .field13(field13)
                .build();

        List<Processor> processors = List.of(new SwapFieldsProcessor(), new EvenSecondExceptionProcessor());

        Consumer<Exception> errorHandler = ex -> System.out.println("Error occurred: " + ex.getMessage());

        ComplexProcessor complexProcessor = new ComplexProcessor(processors, errorHandler);

        HistoryListener historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        Message processedMessage = complexProcessor.handle(message);
        log.info(processedMessage.toString());
    }
}
