package config;

public enum Environment {
    DEV("dev"), QA("qa"), PROD("prod");

    private final String value;

    Environment(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
