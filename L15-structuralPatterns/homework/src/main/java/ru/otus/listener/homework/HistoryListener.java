package ru.otus.listener.homework;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ru.otus.custom_exceptions.ReCheckIdException;
import ru.otus.listener.Listener;
import ru.otus.model.Message;

public class HistoryListener implements Listener, HistoryReader {
    private static final Map<Long, Message> history = new HashMap<>();

    @Override
    public void onUpdated(Message message) {
        if (message.getId() != 0) {
            history.put(message.getId(), message);
        } else {
            throw new ReCheckIdException();
        }
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return Optional.ofNullable(history.get(id));
    }

    public void clearMessages() {
        history.clear();
    }
}
