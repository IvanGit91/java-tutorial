package utility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Objects;

/**
 * Utility class for mathematical operations.
 * Provides methods for rounding, truncating, numeric validation, and arithmetic operations.
 */
public class UtilsMath {

    // ------------ ROUNDING ------------

    /**
     * Rounds a double to 2 decimal places (currency format).
     *
     * @param value Value to round
     * @return Rounded value with 2 decimal places
     */
    public static double roundDoubleCurrency(double value) {
        return roundDouble(value, 2);
    }

    /**
     * Rounds a double to the specified number of decimal places.
     *
     * @param value       Value to round
     * @param numDecimals Number of decimal places
     * @return Rounded value
     */
    public static double roundDouble(double value, int numDecimals) {
        double multiplier = Math.pow(10, numDecimals);
        return Math.round(value * multiplier) / multiplier;
    }

    /**
     * Rounds a double up (ceiling) to 2 decimal places.
     *
     * @param number Number to round
     * @return Rounded value with 2 decimal places
     */
    public static Double ceilDouble(double number) {
        return ceilDouble(number, 2);
    }

    /**
     * Rounds a double to the specified number of decimal places.
     *
     * @param number    Number to round
     * @param nDecimals Number of decimal places
     * @return Rounded value
     */
    public static Double ceilDouble(double number, int nDecimals) {
        double multiplier = Math.pow(10, nDecimals);
        return Math.round(number * multiplier) / multiplier;
    }

    /**
     * Truncates a double to the specified number of decimal places.
     * Uses floor rounding for positive numbers and ceiling for negative.
     *
     * @param x                Value to truncate
     * @param numberOfDecimals Number of decimal places to keep
     * @return Truncated value
     */
    public static Double truncateDecimal(double x, int numberOfDecimals) {
        if (x > 0) {
            return new BigDecimal(String.valueOf(x))
                    .setScale(numberOfDecimals, RoundingMode.FLOOR)
                    .doubleValue();
        } else {
            return new BigDecimal(String.valueOf(x))
                    .setScale(numberOfDecimals, RoundingMode.CEILING)
                    .doubleValue();
        }
    }

    // ------------ NUMERIC VALIDATION ------------

    /**
     * Checks if a value represents a double (contains decimal point).
     *
     * @param value Value to check
     * @return true if value is a valid double with decimal point
     */
    public static <T> boolean isDouble(T value) {
        if (value == null) {
            return false;
        }
        String strValue = value.toString();
        if (!strValue.contains(".")) {
            return false;
        }
        try {
            Double.parseDouble(strValue);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Checks if a string represents a numeric value.
     *
     * @param strNum String to check
     * @return true if string is a valid number
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null || strNum.trim().isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(strNum);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // ------------ ARITHMETIC OPERATIONS ------------

    /**
     * Sums all non-null integers.
     *
     * @param ints Integers to sum
     * @return Sum of all non-null values, 0 if array is null or empty
     */
    public static Integer sumIntegers(Integer... ints) {
        if (ints == null || ints.length == 0) {
            return 0;
        }
        return Arrays.stream(ints)
                .filter(Objects::nonNull)
                .mapToInt(i -> i)
                .sum();
    }

    /**
     * Subtracts all subsequent non-null doubles from the first value.
     *
     * @param vals Doubles to subtract (first value minus all others)
     * @return Result of subtraction, 0 if array is null, empty, or contains only nulls
     */
    public static Double subtractDouble(Double... vals) {
        if (vals == null || vals.length == 0) {
            return 0d;
        }
        return Arrays.stream(vals)
                .filter(Objects::nonNull)
                .mapToDouble(i -> i)
                .reduce((subtotal, element) -> subtotal - element)
                .orElse(0d);
    }

    // ------------ TAX CALCULATIONS ------------

    /**
     * Calculates the VAT (Value Added Tax) amount for a given value.
     *
     * @param value   Base value
     * @param vatRate VAT rate percentage (e.g., 22 for 22%)
     * @return VAT amount
     */
    public static double calculateVAT(double value, double vatRate) {
        return (value / 100) * vatRate;
    }
}
