package callapi.demo.config;

import java.util.*;

import static java.util.stream.Collectors.*;

/**
 * Builds and loads and {@link Configenv} instance.
 *
 * @see Configenv#configure()
 */
public class ConfigenvBuilder {
    private String filename = ".env";
    private String directoryPath = "./";
    private boolean systemProperties = false;
    private boolean throwIfMissing = true;
    private boolean throwIfMalformed = true;

    /**
     * Sets the directory containing the .env file.
     *
     * @param path the directory containing the .env file
     * @return this {@link ConfigenvBuilder}
     */
    public ConfigenvBuilder directory(String path) {
        this.directoryPath = path;
        return this;
    }

    /**
     * Sets the name of the .env file. The default is .env.
     *
     * @param name the filename
     * @return this {@link ConfigenvBuilder}
     */
    public ConfigenvBuilder filename(String name) {
        filename = name;
        return this;
    }

    /**
     * Does not throw an exception when .env is missing.
     *
     * @return this {@link ConfigenvBuilder}
     */
    public ConfigenvBuilder ignoreIfMissing() {
        throwIfMissing = false;
        return this;
    }

    /**
     * Does not throw an exception when .env is malformed.
     *
     * @return this {@link ConfigenvBuilder}
     */
    public ConfigenvBuilder ignoreIfMalformed() {
        throwIfMalformed = false;
        return this;
    }

    /**
     * Sets each environment variable as system properties.
     *
     * @return this {@link ConfigenvBuilder}
     */
    public ConfigenvBuilder systemProperties() {
        systemProperties = true;
        return this;
    }

    /**
     * Load the contents of .env into the virtual environment.
     *
     * @return a new {@link Configenv} instance
     * @throws ConfigenvException when an error occurs
     */
    public Configenv load() throws ConfigenvException {
        ConfigenvParser reader = new ConfigenvParser(
                new ConfigenvReader(directoryPath, filename),
                throwIfMissing,
                throwIfMalformed);
        List<ConfigenvEntry> env = reader.parse();
        if (systemProperties) {
            env.forEach(it -> System.setProperty(it.getKey(), it.getValue()));
        }
        return new DotenvImpl(env);
    }

    static class DotenvImpl implements Configenv {
        private final Map<String, String> envVars;
        private final Set<ConfigenvEntry> set;
        private final Set<ConfigenvEntry> setInFile;
        private final Map<String, String> envVarsInFile;

        public DotenvImpl(List<ConfigenvEntry> envVars) {
            this.envVarsInFile = envVars.stream().collect(toMap(ConfigenvEntry::getKey, ConfigenvEntry::getValue));
            this.envVars = new HashMap<>(this.envVarsInFile);
            System.getenv().forEach(this.envVars::put);

            this.set = this.envVars.entrySet().stream()
                    .map(it -> new ConfigenvEntry(it.getKey(), it.getValue()))
                    .collect(collectingAndThen(toSet(), Collections::unmodifiableSet));

            this.setInFile = this.envVarsInFile.entrySet().stream()
                    .map(it -> new ConfigenvEntry(it.getKey(), it.getValue()))
                    .collect(collectingAndThen(toSet(), Collections::unmodifiableSet));
        }

        @Override
        public Set<ConfigenvEntry> entries() {
            return set;
        }

        @Override
        public Set<ConfigenvEntry> entries(Configenv.Filter filter) {
            if (filter != null) return setInFile;
            return entries();
        }

        @Override
        public String get(String key) {
            String value = System.getenv(key);
            return value != null ? value : envVars.get(key);
        }

        @Override
        public String get(String key, String defaultValue) {
            String value = this.get(key);
            return value != null ? value : defaultValue;
        }
    }
}