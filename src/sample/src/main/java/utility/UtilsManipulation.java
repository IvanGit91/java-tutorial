package utility;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.log4j.Logger;

import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for string manipulation.
 * Provides methods for regex, formatting, and string operations.
 */
public class UtilsManipulation {
    private static final Logger logger = Logger.getLogger(UtilsManipulation.class);

    // ------------ REGEX MATCHING AND SUBSTITUTION ------------
//	public static String ciceroErrorConversion(String error) {
//		String match, convertedError = null;
//		if ((match = match(error, Const.PLATE_MATCHER, 1)) != null) convertedError = Multilanguage.getMessage("cicero.plate.already.inuse", match);
//		else if ((match = match(error, Const.PARKING_MATCHER)) != null) convertedError = Multilanguage.getMessage("cicero.parking.not.exist");
//		else if ((match = match(error, Const.PARKING_EXTEND_MATCHER)) != null) convertedError = Multilanguage.getMessage("cicero.parking.extended.error");
//		else if (match(error, Const.INVALID_MATCHER) != null) convertedError = Multilanguage.getMessage("cicero.invalid.input");
//		return convertedError == null ? error : convertedError;
//	}

    // ------------ REGULAR EXPRESSIONS ------------

    /**
     * Searches for a regex match in the provided text.
     *
     * @param text          Text to analyze
     * @param patternString Regex pattern to search for
     * @return Empty string if match, null if no match
     */
    public static String match(String text, String patternString) {
        return match(text, patternString, null);
    }

    /**
     * Searches for a regex match in the provided text.
     *
     * @param text          Text to analyze
     * @param patternString Regex pattern to search for
     * @param retGroup      Group to return (optional)
     * @return The matching group, empty string if match without groups, null if no match
     */
    public static String match(String text, String patternString, Integer retGroup) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        boolean res = matcher.matches();
        return res && matcher.groupCount() > 0 && retGroup != null ? matcher.group(retGroup) : res ? "" : null;
    }

    // ------------ FORMATTING ------------

    /**
     * Formats a string with positional parameters.
     * E.g.: format("Hello I am {0} {1}", "John", "Doe") → "Hello I am John Doe"
     */
    public static String format(String str, Object... positionalString) {
        return MessageFormat.format(str, positionalString);
    }

    // ------------ STRING MANIPULATION ------------
    // E.g. /237373/257701/257702/257801/
    // E.g.: To get 257701, call parseElement(target, "/", 2, false)
    public static String parseElement(String target, String separator, int nOccurrence, boolean toEndOfString) {
        logger.info("Target: " + target);
        String tempValue = null;
        int begin = 0, index = 0, counter = 0;
        while ((index = target.indexOf(separator, index)) != -1 && counter < nOccurrence) {
            index++;
            counter++;
            begin = index;
        }
        // For the final value
        tempValue = toEndOfString ? target.substring(begin) : target.substring(begin, target.indexOf(separator, index));
        logger.info("VALUE: " + tempValue);
        return tempValue;
    }

    /**
     * Splits a string up to a certain number of occurrences.
     *
     * @param target           String to split
     * @param separator        Separator
     * @param nOccurrence      Number of occurrences to include
     * @param includeSeparator Whether to include the separator in the result
     * @return Resulting string
     */
    public static String splitUntil(String target, String separator, int nOccurrence, boolean includeSeparator) {
        StringBuilder result = new StringBuilder();
        String[] parts = target.split(separator);
        int until = Math.min(nOccurrence, parts.length);
        for (int i = 0; i < until; i++) {
            result.append(parts[i]);
            if (includeSeparator && (i + 1) < until) {
                result.append(StringEscapeUtils.unescapeJava(separator));
            }
        }
        return result.toString();
    }

    /**
     * Parses all elements from a string and returns them as an array.
     *
     * @param target        String to parse
     * @param separator     Separator
     * @param toEndOfString If true, includes the last element until end of string
     * @return Array of extracted elements
     */
    public static String[] parseAllElementInArray(String target, String separator, boolean toEndOfString) {
        logger.info("Target: " + target);
        List<String> elements = new ArrayList<>();
        int begin = 0, index = 0;
        while ((index = target.indexOf(separator, index)) != -1) {
            elements.add(target.substring(begin, index));
            logger.info("VALUE: " + elements.get(elements.size() - 1));
            index++;
            begin = index;
        }
        // For the final value
        if (toEndOfString) {
            elements.add(target.substring(begin));
        } else {
            int nextSeparator = target.indexOf(separator, begin);
            if (nextSeparator != -1) {
                elements.add(target.substring(begin, nextSeparator));
            } else {
                elements.add(target.substring(begin));
            }
        }
        return elements.toArray(new String[0]);
    }

    /**
     * Removes the first element from a string array.
     *
     * @param target Source array
     * @return New array without the first element
     */
    public static String[] popFromString(String[] target) {
        if (target == null || target.length <= 1) {
            return new String[0];
        }
        String[] result = new String[target.length - 1];
        System.arraycopy(target, 1, result, 0, target.length - 1);
        return result;
    }

    /**
     * Joins a string array with comma as separator.
     *
     * @param target Array to join
     * @return Resulting string
     */
    public static String splitString(String[] target) {
        return String.join(", ", target);
    }

    /**
     * Counts non-null elements in an array.
     *
     * @param target Array to analyze
     * @return Number of non-null elements
     */
    public static int countArrayElements(String[] target) {
        int counter = 0;
        for (String s : target) {
            if (s != null) {
                counter++;
            }
        }
        return counter;
    }

    /**
     * Inserts a string every N characters.
     *
     * @param target Original string
     * @param offset Insertion interval
     * @param param  String to insert
     * @return Modified string
     */
    public static String insertBetweenString(String target, int offset, String param) {
        if (target == null || offset <= 0) {
            return target;
        }
        StringBuilder result = new StringBuilder();
        int counter = 0;
        while (target.length() - counter > offset) {
            result.append(target, counter, counter + offset).append(param);
            counter += offset;
        }
        result.append(target.substring(counter));
        return result.toString();
    }

    /**
     * Finds the position of a string in an array.
     *
     * @param array  Array to search in
     * @param search String to search for
     * @return Index of the string, null if not found
     */
    public static Integer stringPosition(String[] array, String search) {
        if (array == null || search == null) {
            return null;
        }
        for (int i = 0; i < array.length; i++) {
            if (search.equals(array[i])) {
                return i;
            }
        }
        return null;
    }

    /**
     * Finds the index of the longest string in an array.
     *
     * @param s Array of strings
     * @return Index of the longest string
     */
    public static int findMax(String[] s) {
        if (s == null || s.length == 0) {
            return -1;
        }
        int index = 0, max = 0;
        for (int i = 0; i < s.length; i++) {
            if (s[i] != null && s[i].length() > max) {
                max = s[i].length();
                index = i;
            }
        }
        return index;
    }

    /**
     * Capitalizes the first letter of a string.
     *
     * @param str String to modify
     * @return String with first letter capitalized
     */
    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * Capitalizes the first letter of each word in a string.
     *
     * @param s String to be modified
     * @return String with all words having only the first letter capitalized.
     */
    public static String capitalizeFirstLetterInAllWordsInAString(String s) {
        if (s == null || s.isEmpty()) {
            return s;
        }
        String[] parts = s.split(" ");
        for (int k = 0; k < parts.length; k++) {
            if (!parts[k].isEmpty()) {
                parts[k] = parts[k].substring(0, 1).toUpperCase() + parts[k].substring(1).toLowerCase();
            }
        }
        return String.join(" ", parts).trim();
    }

    /**
     * Extracts the filename from a path (cross-platform).
     *
     * @param path File path
     * @return Filename
     */
    public static String filenameFromPath(String path) {
        if (path == null || path.isEmpty()) {
            return path;
        }
        return Paths.get(path).getFileName().toString();
    }

    /**
     * Replaces all special characters with underscore.
     *
     * @param target String to process
     * @return String with special characters replaced
     */
    public static String replaceAllLR(String target) {
        if (target == null) {
            return null;
        }
        return target.replaceAll("[$&+,:;=?@#|'<>.^*()%!//\\\"-]", "_").trim();
    }

    /**
     * Removes all whitespace from a string.
     *
     * @param target String to process
     * @return String without spaces
     */
    public static String replaceBlankSpace(String target) {
        if (target == null) {
            return null;
        }
        return target.replaceAll("\\s+", "");
    }

}
