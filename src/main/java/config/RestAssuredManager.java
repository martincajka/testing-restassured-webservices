package config;

import io.restassured.RestAssured;

public class RestAssuredManager {

    public static void configure(String configFolder) {
        Environment environment = System.getProperty("env") == null ? Environment.DEV : Environment.valueOf(System.getProperty("env").toUpperCase());
        ConfigurationProvider configurationProvider = new PropertiesConfigurationProvider(environment, configFolder);
        configurationProvider.getConfiguration().forEach(RestAssuredManager::setConfiguration);
    }

    private static void setConfiguration(ConfigOptions option, String value) {
        switch (option) {
            case BASE_URL:
                RestAssured.baseURI = value;
                break;
            default:
                throw new IllegalArgumentException("Unknown configuration option: " + option);
        }
    }
}
