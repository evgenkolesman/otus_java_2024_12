package ru.otus.dataprocessor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class FileSerializer implements Serializer {
    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try {
            ObjectMapperFactory.mapper.writeValue(new FileWriter(fileName), new TreeMap<>(data));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
