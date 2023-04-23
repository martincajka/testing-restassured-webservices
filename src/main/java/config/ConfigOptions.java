package config;

public enum ConfigOptions {
    BASE_URL("base_url");

    private final String value;

    ConfigOptions(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
