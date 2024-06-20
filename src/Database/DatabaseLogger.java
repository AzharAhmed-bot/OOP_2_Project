package Database;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseLogger {

    private static final Logger LOGGER = Logger.getLogger(DatabaseLogger.class.getName());

    public static void logInfo(String message) {
        LOGGER.info(message);
    }

    public static void logError(String message, Throwable throwable) {
        LOGGER.log(Level.SEVERE, message, throwable);
    }
}
