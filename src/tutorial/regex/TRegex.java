package tutorial.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TRegex {

    public static void main(String[] args) {
        // match();
    }

    public static void match() {
        String match = check("Plate DY12F5 already in use", "Plate (.*) already in use", 1);
        System.out.println("MATCH: " + match);
        match = check("Plate DY12F5 alresady in use", "Plate (.*) already in use", 1);
        System.out.println("MATCH: " + match);
    }

    /**
     * Searches for a regex match in the provided text.
     *
     * @param text          Text to analyze
     * @param patternString Regex pattern to search for
     * @param retGroup      Group to return (optional)
     * @return The matching group, empty string if match without groups, null if no match
     */
    public static String check(String text, String patternString, Integer retGroup) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(text);
        boolean res = matcher.matches();
        return res && matcher.groupCount() > 0 && retGroup != null ? matcher.group(retGroup) : res ? "" : null;
    }
}
