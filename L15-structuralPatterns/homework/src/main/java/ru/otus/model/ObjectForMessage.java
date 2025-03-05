package ru.otus.model;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class ObjectForMessage {
    private List<String> data;

    public void setData(List<String> data) {
        this.data = new ArrayList<>(data);
    }
}
