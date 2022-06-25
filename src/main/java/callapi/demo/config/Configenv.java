package callapi.demo.config;



import java.util.Set;

/**
 * Creates and configures a new Dotenv instance
 */
public interface Configenv {

    /**
     * A dotenv entry filter
     */
    enum Filter {
        /**
         * Filter matching only environment variables declared in the .env file
         */
        DECLARED_IN_ENV_FILE
    }

    /**
     * Configures a new {@link Configenv} instance
     * @return a new {@link Configenv} instance
     */
    static ConfigenvBuilder configure() {
        return new ConfigenvBuilder();
    }

    /**
     * Creates and loads a {@link Configenv} instance with default options
     * @return a new {@link Configenv} instance
     */
    static Configenv load() {
        return new ConfigenvBuilder().load();
    }

    /**
     * Returns the set of environment variables with values
     * @return the set of {@link ConfigenvEntry}s for all environment variables
     */
    Set<ConfigenvEntry> entries();

    /**
     * Returns the set of {@link ConfigenvEntry}s matching the the filter
     * @param filter the filter e.g. {@link Dotenv.Filter}
     * @return the set of {@link ConfigenvEntry}s for environment variables matching the {@link Dotenv.Filter}
     */
    Set<ConfigenvEntry> entries(Filter filter);

    /**
     * Retrieves the value of the environment variable specified by key
     * @param key the environment variable
     * @return the value of the environment variable
     */
    String get(String key);

    /**
     * Retrieves the value of the environment variable specified by key.
     * If the key does not exist, then the default value is returned
     * @param key the environment variable
     * @param defaultValue the default value to return
     * @return the value of the environment variable or default value
     */
    String get(String key, String defaultValue);
}