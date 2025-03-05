package ru.otus.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ru.otus.custom_exceptions.ReCheckIdException;

public class MessageHistory {
    private static final Map<Long, Message> history = new HashMap<>();

    public static void add(Message message) {
        if (message.getId() != 0) {
            history.put(message.getId(), message);
        } else {
            throw new ReCheckIdException();
        }
    }

    public static Optional<Message> findById(Long id) {
        return Optional.ofNullable(history.get(id));
    }

    public static void clear() {
        history.clear();
    }
}
