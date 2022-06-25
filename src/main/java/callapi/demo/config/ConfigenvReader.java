package callapi.demo.config;



import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (Internal) Reads a .env file
 */
public class ConfigenvReader {
    private final String directory;
    private final String filename;

    /**
     * Creates a dotenv reader
     * @param directory the directory containing the .env file
     * @param filename the file name of the .env file e.g. .env
     */
    public ConfigenvReader(String directory, String filename) {
        this.directory = directory;
        this.filename = filename;
    }

    /**
     * (Internal) Reads the .env file
     * @return a list containing the contents of each line in the .env file
     * @throws ConfigenvException if a dotenv error occurs
     * @throws IOException if an I/O error occurs
     */
    public List<String> read() throws ConfigenvException, IOException {
        String dir = directory
                .replaceAll("\\\\", "/")
                .replaceFirst("\\.env$", "")
                .replaceFirst("/$", "");

        String location = dir + "/" + filename;
        String lowerLocation = location.toLowerCase();
        Path path = lowerLocation.startsWith("file:") || lowerLocation.startsWith("android.resource:")
                ? Paths.get(URI.create(location))
                : Paths.get(location);

        if (Files.exists(path)) {
            return Files
                    .lines(path)
                    .collect(Collectors.toList());
        }

        try {
            return ClasspathHelper
                    .loadFileFromClasspath(location.replaceFirst("^\\./", "/"))
                    .collect(Collectors.toList());
        } catch (ConfigenvException e) {
            Path cwd = FileSystems.getDefault().getPath(".").toAbsolutePath().normalize();
            String cwdMessage = !path.isAbsolute() ? "(working directory: " + cwd + ")" : "";
            e.addSuppressed(new ConfigenvException("Could not find " + path + " on the file system " + cwdMessage));
            throw e;
        }
    }
}