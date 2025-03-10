package ru.otus.processor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.time.Instant;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.custom_exceptions.EvenException;
import ru.otus.model.Message;

class EvenSecondExceptionProcessorTest {

    private static final Logger log = LoggerFactory.getLogger(EvenSecondExceptionProcessorTest.class);

    @Test
    void shouldThrowExceptionOnEvenSeconds() {
        EvenSecondExceptionProcessor processor = new EvenSecondExceptionProcessor(Instant::now);
        Message message = Message.builder().build();

        long currentSecond = Instant.now().getEpochSecond();
        if (currentSecond % 2 == 0) {
            log.info("even second: {}", currentSecond);
            assertThrows(EvenException.class, () -> processor.process(message));
        } else {
            log.info("odd second: {}", currentSecond);
            assertDoesNotThrow(() -> processor.process(message));
        }
    }

    @Test
    void testSpecificEvenSecond() {
        EvenSecondExceptionProcessor processor = new EvenSecondExceptionProcessor(Instant::now);
        Message message = Message.builder().build();
        Instant mockedInstant = mock(Instant.class);
        when(mockedInstant.getEpochSecond()).thenReturn(2L); // even second

        try (MockedStatic<Instant> instantMockedStatic = mockStatic(Instant.class)) {
            instantMockedStatic.when(Instant::now).thenReturn(mockedInstant);

            assertThrows(EvenException.class, () -> processor.process(message));
        }
    }

    @Test
    void testSpecificOddSecond() {
        EvenSecondExceptionProcessor processor = new EvenSecondExceptionProcessor(Instant::now);
        Message message = Message.builder().build();

        Instant mockedInstant = mock(Instant.class);
        when(mockedInstant.getEpochSecond()).thenReturn(3L);

        try (MockedStatic<Instant> instantMockedStatic = mockStatic(Instant.class)) {
            instantMockedStatic.when(Instant::now).thenReturn(mockedInstant);

            assertDoesNotThrow(() -> processor.process(message));
        }
    }
}
