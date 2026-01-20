package utility;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class for common operations.
 * Provides methods for array conversion, null handling, parsing, and value defaults.
 */
public class UtilsCommon {

    private static final Logger logger = Logger.getLogger(UtilsCommon.class);

    // ------------ ARRAY CONVERSION ------------

    /**
     * Converts varargs to an Object array.
     *
     * @param objects Varargs of objects
     * @return Object array
     */
    public static Object[] toArray(Object... objects) {
        return objects;
    }

    /**
     * Converts varargs to a String array.
     *
     * @param strings Varargs of strings
     * @return String array
     */
    public static String[] toArray(String... strings) {
        return strings;
    }

    /**
     * Converts varargs to an Integer array.
     *
     * @param ints Varargs of integers
     * @return Integer array
     */
    public static Integer[] toArray(Integer... ints) {
        return ints;
    }

    // ------------ DEFAULT VALUES ------------

    /**
     * Gets the default value for a given type.
     *
     * @param value TValue to check type
     * @return Default value: 0 for Integer, 0.0 for Double, empty string for others
     */
    public static <T> Object getDefValue(T value) {
        if (value == null) {
            return null;
        } else if (value instanceof Integer) {
            return 0;
        } else if (value instanceof Double) {
            return 0d;
        } else {
            // String or others
            return "";
        }
    }

    // ------------ VALUE OR EMPTY/DEFAULT ------------

    /**
     * Returns the value or its default if null/empty.
     *
     * @param value TValue to check
     * @return TValue or default
     */
    public static <T> Object vOrE(T value) {
        return vOrE(value, false);
    }

    /**
     * Returns the value or a custom default if null/empty.
     *
     * @param value    TValue to check
     * @param defValue Default value to return
     * @return TValue or default
     */
    public static <T> Object vOrE(T value, Object defValue) {
        return vOrE(value, false, defValue);
    }

    /**
     * Returns the value or default, with iteration flag.
     *
     * @param value TValue to check
     * @param iter  If true, returns null instead of default for null values
     * @return TValue or default
     */
    public static <T> Object vOrE(T value, boolean iter) {
        return vOrE(value, iter, getDefValue(value));
    }

    /**
     * Returns the value or custom default, with iteration flag.
     *
     * @param value    TValue to check
     * @param iter     If true, returns null instead of default for null values
     * @param defValue Default value to return
     * @return TValue or default
     */
    public static <T> Object vOrE(T value, boolean iter, Object defValue) {
        return vOrE(value, iter, false, defValue);
    }

    /**
     * Returns the value with optional parsing, or default.
     *
     * @param value TValue to check
     * @param parse If true, attempts to parse string to number
     * @return TValue or default
     */
    public static <T> Object vOrEParse(T value, boolean parse) {
        return vOrEParse(value, false, getDefValue(value));
    }

    /**
     * Returns the value with optional parsing, or custom default.
     *
     * @param value    TValue to check
     * @param parse    If true, attempts to parse string to number
     * @param defValue Default value to return
     * @return TValue or default
     */
    public static <T> Object vOrEParse(T value, boolean parse, Object defValue) {
        return vOrE(value, false, parse, defValue);
    }

    /**
     * Core method for value or empty/default handling.
     *
     * @param value    TValue to check
     * @param iter     If true, returns null/default for iteration scenarios
     * @param parse    If true, attempts to parse string to number
     * @param defValue Default value to return
     * @return TValue or default based on conditions
     */
    public static <T> Object vOrE(T value, boolean iter, boolean parse, Object defValue) {
        Object newObj = value;
        if (value instanceof String && parse) {
            if (notNullEmpty(value.toString()) && UtilsMath.isNumeric(value.toString())) {
                if (value instanceof Integer) {
                    newObj = tryParseInt(newObj.toString(), 0);
                } else {
                    newObj = tryParseDouble(newObj.toString(), 0d);
                }
            }
        }
        if (newObj == null) {
            // NULL Object
            return iter ? null : defValue;
        } else if (newObj instanceof String) {
            return iter ? defValue : newObj.equals("") ? defValue : newObj.toString();
        } else if (newObj instanceof Integer) {
            return iter ? defValue : newObj;
        } else if (newObj instanceof Double) {
            return iter ? defValue : newObj;
        } else {
            logger.error("Unhandled case " + newObj + " class: " + newObj.getClass());
            return iter ? null : newObj;
        }
    }

    /**
     * Returns the double value or 0 if null.
     *
     * @param value String value to parse
     * @return Double value or 0
     */
    public static Double valueOrEmptyDub(String value) {
        return value == null ? 0d : tryParseDouble(value, 0d);
    }

    /**
     * Returns the integer value or 0 if null.
     *
     * @param value String value to parse
     * @return Integer value or 0
     */
    public static Integer valueOrEmptyInt(String value) {
        return value == null ? 0 : tryParseInt(value, 0);
    }

    /**
     * Returns the value as string or empty string if null.
     *
     * @param value TValue to convert
     * @return String value or empty string
     */
    public static <T> String valueOrEmpty(T value) {
        return value == null || value.equals("") ? "" : value.toString();
    }

    /**
     * Returns the value as string or empty string based on iteration flag.
     *
     * @param value TValue to convert
     * @param iter  If true, returns empty string
     * @return String value or empty string
     */
    public static <T> String valueOrEmpty(T value, boolean iter) {
        if (iter || value == null) {
            return "";
        }
        return value.toString();
    }

    /**
     * Returns the number value or 0 if null.
     *
     * @param value Number value
     * @return Number value or 0
     */
    public static Number valueOrEmptyNum(Number value) {
        return value == null ? 0 : value;
    }

    /**
     * Returns the number value or null based on iteration flag.
     *
     * @param value Number value
     * @param iter  If true, returns null
     * @return Number value or null
     */
    public static Number valueOrEmptyNum(Number value, boolean iter) {
        return iter ? null : value;
    }

    /**
     * Parses string to integer or returns 0 if null/empty.
     *
     * @param value String value to parse
     * @return Integer value or 0
     */
    public static Integer valueOrEmptyStrInt(String value) {
        return value == null || value.isEmpty() ? 0 : tryParseInt(value, 0);
    }

    /**
     * Parses string to double or returns 0 if null/empty.
     *
     * @param value String value to parse
     * @return Double value or 0
     */
    public static Double valueOrEmptyStrDub(String value) {
        return value == null || value.isEmpty() ? 0d : tryParseDouble(value, 0d);
    }

    /**
     * Returns the value or "[N.D]" placeholder if null.
     *
     * @param value TValue to check
     * @return TValue or "[N.D]" (Not Defined)
     */
    @SuppressWarnings("unchecked")
    public static <T> T nullZero(T value) {
        return value == null ? (T) "[N.D]" : value;
    }

    /**
     * Returns the value as string or empty string if null.
     *
     * @param value TValue to check
     * @return String value or empty string
     */
    public static <T> String nullZeroEmpty(T value) {
        return value == null ? "" : value.toString();
    }

    /**
     * Checks if a string is not null and not empty.
     *
     * @param s String to check
     * @return true if string is not null and not empty after trimming
     */
    public static boolean notNullEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    // ------------ ELEMENT PRESENCE ------------

    /**
     * Checks if an element exists at the specified position in the array.
     *
     * @param array Array to check
     * @param pos   Position to check
     * @return true if element exists at position
     */
    public static <X> boolean present(X[] array, int pos) {
        return array != null && pos < array.length && array[pos] != null;
    }

    /**
     * Checks if an element exists in the array.
     *
     * @param array Array to search
     * @param elem  Element to find
     * @return true if element is found
     */
    public static <X> boolean present(X[] array, X elem) {
        if (array == null || elem == null) {
            return false;
        }
        return Arrays.stream(array).anyMatch(elem::equals);
    }

    /**
     * Checks if a string exists in the array (case-insensitive).
     *
     * @param array Array to search
     * @param elem  String to find
     * @return true if string is found (ignoring case)
     */
    public static boolean present(String[] array, String elem) {
        if (array == null || elem == null) {
            return false;
        }
        return Arrays.stream(array)
                .map(e -> e.toLowerCase().trim())
                .anyMatch(elem.toLowerCase().trim()::equals);
    }

    // ------------ PARSING ------------

    /**
     * Tries to parse a string to integer, returns default on failure.
     *
     * @param value  String to parse
     * @param defVal Default value if parsing fails
     * @return Parsed integer or default value
     */
    public static Integer tryParseInt(String value, int defVal) {
        if (value == null) {
            return defVal;
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defVal;
        }
    }

    /**
     * Tries to parse a string to double, returns default on failure.
     *
     * @param value  String to parse
     * @param defVal Default value if parsing fails
     * @return Parsed double or default value
     */
    public static double tryParseDouble(String value, double defVal) {
        if (value == null) {
            return defVal;
        }
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return defVal;
        }
    }

    // ------------ ERROR HANDLING ------------

    /**
     * Gets the chain of causes from an exception.
     *
     * @param e Exception to analyze
     * @return String containing all causes
     */
    public static String getCauses(Exception e) {
        if (e == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        Throwable t = e.getCause();
        while (t != null) {
            result.append(t);
            t = t.getCause();
        }
        return result.toString();
    }

    // ------------ LIST UTILITIES ------------

    /**
     * Converts a list to an array.
     * Currently only supports String lists.
     *
     * @param list List to convert
     * @return Array of elements, null if empty or unsupported type
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] listToArray(List<T> list) {
        if (list != null && !list.isEmpty()) {
            T type = list.get(0);
            if (type instanceof String) {
                return (T[]) list.toArray(String[]::new);
            }
        }
        return null;
    }
}
