package ru.otus.dataprocessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {

    private static final Logger log = LoggerFactory.getLogger(ResourcesFileLoader.class);
    private final Path fileName;

    public ResourcesFileLoader(String fileName) {
        this.fileName = Paths.get("src/test/resources/%s".formatted(fileName));
    }

    @Override
    public List<Measurement> load() {
        List<Measurement> measurements;

        File jsonFile = fileName.toFile();
        final var mapper = ObjectMapperFactory.mapper;
        try {
            measurements = mapper.readValue(
                    jsonFile, mapper.getTypeFactory().constructCollectionType(List.class, Measurement.class));
        } catch (IOException e) {
            log.error(e.getMessage(), e.getCause());
            measurements = new ArrayList<>();
        }
        // читает файл, парсит и возвращает результат
        return measurements;
    }
}
