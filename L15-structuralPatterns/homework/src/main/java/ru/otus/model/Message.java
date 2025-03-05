package ru.otus.model;

import java.util.ArrayList;
import lombok.Builder;
import lombok.Value;
import lombok.With;

@Value
@Builder(toBuilder = true)
@With
public class Message {
    long id;
    String field1;
    String field2;
    String field3;
    String field4;
    String field5;
    String field6;
    String field7;
    String field8;
    String field9;
    String field10;
    String field11;
    String field12;
    ObjectForMessage field13;

    public ObjectForMessage getField13() {
        var objectForMessage = new ObjectForMessage();
        objectForMessage.setData(new ArrayList<>(field13.getData()));
        return objectForMessage;
    }
}
