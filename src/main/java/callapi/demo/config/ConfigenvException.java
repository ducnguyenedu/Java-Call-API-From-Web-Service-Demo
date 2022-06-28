package callapi.demo.config;

/**
 * Signals that dotenv exception of some sort has occurred.
 */
public class ConfigenvException extends RuntimeException {
    /**
     * Create a dotenv runtime exception with the specified detail message
     *
     * @param message the detail message
     */
    public ConfigenvException(String message) {
        super(message);
    }

    /**
     * Creates a dotenv runtime exception
     *
     * @param cause the cause
     */
    public ConfigenvException(Throwable cause) {
        super(cause);
    }
}