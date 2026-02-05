package newfeatures.v14;

import java.io.IOException;

public class Java14 {
    public static void main(String[] args) throws IOException {
        // 2.1. Switch Expressions (JEP 361)
        // old switch
        String day = "MONDAY";
        boolean isTodayHoliday;
        switch (day) {
            case "MONDAY":
            case "TUESDAY":
            case "WEDNESDAY":
            case "THURSDAY":
            case "FRIDAY":
                isTodayHoliday = false;
                break;
            case "SATURDAY":
            case "SUNDAY":
                isTodayHoliday = true;
                break;
            default:
                throw new IllegalArgumentException("What's a " + day);
        }
        // NEW SWITCH
        boolean isTodayHoliday2 = switch (day) {
            case "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY" -> false;
            case "SATURDAY", "SUNDAY" -> true;
            default -> throw new IllegalArgumentException("What's a " + day);
        };

        // 2.2. Text Blocks (JEP 368)
        // \: to indicate the end of the line, so that a new line character is not introduced
        // \s: to indicate a single space
        // old
        String multiline = "A quick brown fox jumps over a lazy dog; the lazy dog howls loudly.";
        // new
        String multiline2 = """
                A quick brown fox jumps over a lazy dog; \
                the lazy dog howls loudly.""";
    }
}
