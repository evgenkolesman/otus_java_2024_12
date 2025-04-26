package ru.otus.jdbc.mapper;

import java.io.InputStream;
import java.util.Map;
import lombok.Getter;
import org.yaml.snakeyaml.Yaml;
import ru.otus.HomeWork;

public class ResourcesUtil {

    @Getter
    private static final String schema = initSchema();

    @SuppressWarnings("ALL")
    public static String initSchema() {
        Yaml yaml = new Yaml();
        InputStream inputStream = HomeWork.class.getClassLoader().getResourceAsStream("application.yml");
        Map<String, Object> obj = yaml.load(inputStream);
        return ((Map<String, String>) obj.get("db")).get("schema");
    }
}
