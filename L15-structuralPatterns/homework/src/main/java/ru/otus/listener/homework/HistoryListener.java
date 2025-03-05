package ru.otus.listener.homework;

import java.util.Optional;
import ru.otus.listener.Listener;
import ru.otus.model.Message;
import ru.otus.model.MessageHistory;

public class HistoryListener implements Listener, HistoryReader {

    @Override
    public void onUpdated(Message msg) {
        MessageHistory.add(msg);
    }

    @Override
    public Optional<Message> findMessageById(long id) {
        return MessageHistory.findById(id);
    }

    public void clearMessages() {
        MessageHistory.clear();
    }
}
