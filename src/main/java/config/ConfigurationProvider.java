package config;

import java.util.HashMap;

public interface ConfigurationProvider {
    HashMap<ConfigOptions, String> getConfiguration();
}
