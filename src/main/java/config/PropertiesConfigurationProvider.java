package config;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

public class PropertiesConfigurationProvider implements ConfigurationProvider {

    private final Environment environment;
    private final String configFolderName;


    PropertiesConfigurationProvider(Environment environment, String configFolderName) {
        this.environment = environment;
        this.configFolderName = configFolderName;
    }

    private Properties readFromPropertiesFile(Environment environment) {

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource(configFolderName + "/" + environment.getValue() + ".properties");
        if (resource == null) {
            throw new IllegalArgumentException("file is not found!");
        }

        Properties properties = new Properties();
        try {
            properties.load(Objects.requireNonNull(resource).openStream());
        } catch (IOException e) {
            throw new RuntimeException("Issue with config resource file: \n" + e);
        }

        return properties;
    }

    @Override
    public HashMap<ConfigOptions, String> getConfiguration() {
        Properties configuration = readFromPropertiesFile(environment);

        return configuration.entrySet().stream()
                .collect(HashMap::new,
                        (map, entry) -> map.put(ConfigOptions.valueOf(entry.getKey().toString().toUpperCase()), entry.getValue().toString()),
                        HashMap::putAll);
    }


}
