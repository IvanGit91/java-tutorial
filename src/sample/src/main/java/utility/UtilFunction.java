package utility;

import org.apache.log4j.Logger;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;


public class UtilFunction {

    private final static Logger logger = Logger.getLogger(UtilFunction.class);
    public static Double THRESHOLD = 0.1d;

    // COMMON UTILS //

    public static String[] strings(String... strings) {
        return strings;
    }

    public static Integer[] strings(Integer... ints) {
        return ints;
    }

    public static <T> String valueOrEmpty(T value) {
        return value == null || value.equals("") ? "" : value.toString();
    }

    public static <T> Double valueOrEmptyDub(String value) {
        return value == null ? 0 : tryParseDouble(value, 0);
    }

    public static <T> Integer valueOrEmptyInt(String value) {
        return value == null ? 0 : tryParseInt(value, 0);
    }

    public static Number valueOrEmptyNum(Number value) {
        return value == null ? 0 : value;
    }

    public static Integer valueOrEmptyStrInt(String value) {
        return value == null || value.equals("") ? 0 : tryParseInt(value, 0);
    }

    public static Double valueOrEmptyStrDub(String value) {
        return value == null || value.equals("") ? 0 : tryParseDouble(value, 0);
    }

    public static Integer sumIntegers(Integer... ints) {
        return ints != null ? Arrays.asList(ints).stream().filter(i -> i != null).mapToInt(i -> i).sum() : 0;
    }

    public static String replaceAllLR(String target) {
        return target.replaceAll("[$&+,:;=?@#|'<>.^*()%!//\\\"-]", "_").trim();
    }

    public static String replaceBlankSpace(String target) {
        return target.replaceAll("\\s+", "");
    }

    @SuppressWarnings("unchecked")
    public static <T> T nullZero(T value) {
        return value == null ? (T) "[N.D]" : value;
    }

    public static <T> String nullZeroEmpty(T value) {
        return value == null ? "" : value.toString();
    }


    public static int countArrayElements(String[] target) {
        int counter = 0;
        for (int i = 0; i < target.length; i++)
            if (target[i] != null)
                counter++;
        return counter;
    }


    public static <X> boolean present(X[] array, int pos) {
        return array != null && pos < array.length && array[pos] != null;
    }

    public static <X> boolean present(X[] array, X elem) {
        return Arrays.stream(array).anyMatch(elem::equals);
    }

    public static <X> boolean presentL(String[] array, String elem) {
        return Arrays.stream(array).map(e -> e.toLowerCase().trim()).anyMatch(elem.toLowerCase().trim()::equals);
    }

//	public static <T> String nullZero(T value) {
//		return value == null ? "[N.D]" : value.toString();
//	}

    public static String insertBetweenString(String target, int offset, String param) {
        String res = "";
        int counter = 0;
        while (target.substring(counter).length() > offset) {
            res += target.substring(counter, counter + offset) + param;
            counter += offset;
        }
        return res += target.substring(counter);
    }

    public static Integer StringPosition(String[] array, String search) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(search))
                return i;
        }
        return null;
    }

    public static Double truncateDecimal(double x, int numberofDecimals) {
        if (x > 0) {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, RoundingMode.FLOOR).doubleValue();
        } else {
            return new BigDecimal(String.valueOf(x)).setScale(numberofDecimals, RoundingMode.CEILING).doubleValue();
        }
    }

    public static <T> boolean isDouble(T value) {
        boolean res = false;
        if (value.toString().contains(".")) {
            try {
                Double.parseDouble(value.toString());
                res = true;
            } catch (NumberFormatException e) {
                //not a double
            }
        }
        return res;
    }

    public static Integer tryParseInt(String value, int defVal) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return defVal;
        }
    }

    public static double tryParseDouble(String value, int defVal) {
        try {
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return defVal;
        }
    }

    public static int findMax(String[] s) {
        int index = 0, max = 0;
        for (int i = 0; i < s.length; i++) {
            if (max < s[i].length()) {
                max = s[i].length();
                index = i;
            }
        }
        return index;
    }

    public static void createDirs(String path) {
        File dir = new File(path);
        if (!dir.exists())
            dir.mkdirs();
    }

}
