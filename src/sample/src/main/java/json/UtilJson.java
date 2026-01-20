package json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.FileSystems;

/**
 * Utility class for JSON operations using Gson.
 * Provides common helper methods for JSON serialization and deserialization.
 */
public abstract class UtilJson {

    protected static final String DATA_FOLDER = System.getProperty("user.home") +
            FileSystems.getDefault().getSeparator() + "Documents" +
            FileSystems.getDefault().getSeparator() + "Json" +
            FileSystems.getDefault().getSeparator();

    private static final Logger logger = LoggerFactory.getLogger(UtilJson.class);

    /**
     * Checks if a string is not null and not empty (after trimming).
     *
     * @param value the string to check
     * @return true if the string has content, false otherwise
     */
    protected static boolean isNotBlank(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Creates a Gson instance configured for pretty printing.
     *
     * @return configured Gson instance
     */
    protected static Gson createGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    /**
     * Creates a Gson instance with custom configuration.
     *
     * @param prettyPrint    whether to enable pretty printing
     * @param serializeNulls whether to serialize null values
     * @return configured Gson instance
     */
    protected static Gson createGson(boolean prettyPrint, boolean serializeNulls) {
        GsonBuilder builder = new GsonBuilder();
        if (prettyPrint) {
            builder.setPrettyPrinting();
        }
        if (serializeNulls) {
            builder.serializeNulls();
        }
        return builder.create();
    }

    /**
     * Converts an object to a JSON string.
     *
     * @param object the object to serialize
     * @return JSON string representation
     */
    protected static String toJson(Object object) {
        return createGson().toJson(object);
    }

    /**
     * Parses a JSON string into an object of the specified type.
     *
     * @param json     the JSON string to parse
     * @param classOfT the class of the target type
     * @param <T>      the target type
     * @return the parsed object
     */
    protected static <T> T fromJson(String json, Class<T> classOfT) {
        return createGson().fromJson(json, classOfT);
    }
}
