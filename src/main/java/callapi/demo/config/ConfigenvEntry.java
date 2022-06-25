package callapi.demo.config;



/**
 * A key value pair representing an environment variable and its value
 */
public class ConfigenvEntry {

    private final String key;
    private final String value;

    /**
     * Creates a new dotenv entry using the provided key and value
     * @param key the dotenv entry name
     * @param value the dotenv entry value
     */
    public ConfigenvEntry(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key for the {@link ConfigenvEntry}
     * @return the key for the {@link ConfigenvEntry}
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the value for the {@link ConfigenvEntry}
     * @return the value for the {@link ConfigenvEntry}
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key+"="+value;
    }
}
