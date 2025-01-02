package pl.magzik.zomboidbooktracker.base;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathResolver {

    private static class InstanceHolder {
        private static final PathResolver INSTANCE = new PathResolver();
    }

    public static PathResolver getInstance() {
        return InstanceHolder.INSTANCE;
    }

    private final Path dataDirectory;
    private final Path logDirectory;

    private static final String DATA_FOLDER = "data",
                                LOG_FOLDER = "log";

    private static final String WIN_PATH = "AppData/Roaming/ZomboidBookTracker/",
                                MAC_PATH = "Library/Application Support/ZomboidBookTracker/",
                                LINUX_PATH = ".config/ZomboidBookTracker/";

    private PathResolver() {
        String userHome = System.getProperty("user.home");
        if (userHome == null) throw new IllegalStateException("User home directory is not available.");

        String operatingSystem = System.getProperty("os.name").toLowerCase();
        Path applicationPath = getApplicationPath(userHome, operatingSystem);

        this.dataDirectory = createDirectories(applicationPath, DATA_FOLDER);
        this.logDirectory = createDirectories(applicationPath, LOG_FOLDER);
    }

    private Path getApplicationPath(String userHome, String operatingSystem) {
        if (operatingSystem.contains("win")) return Paths.get(userHome, WIN_PATH);
        else if (operatingSystem.contains("mac")) return Paths.get(userHome, MAC_PATH);
        else return Paths.get(userHome, LINUX_PATH);
    }

    private Path createDirectories(Path base, String folder) {
        Path folderPath = base.resolve(folder);
        try {
            Files.createDirectories(folderPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return folderPath;
    }

    public Path getDataDirectory() {
        return dataDirectory;
    }

    public Path getLogDirectory() {
        return logDirectory;
    }
}
