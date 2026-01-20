package utility;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Const {
    public static final String TIMEZONE_CEST = "Europe/Rome";
    public static final String CUSTOM_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter EUROPEAN_DATE_FORMATTER = DateTimeFormatter.ofPattern(CUSTOM_FORMAT_STRING);
    public static final ZoneId ZONE_ROME = ZoneId.of(TIMEZONE_CEST);
    public static final SimpleDateFormat JSF_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    public static final SimpleDateFormat FORMAT_YEAR = new SimpleDateFormat("yyyy");
    public static final String FAVORITE_COLOR = "green";
    public static final String EXECUTION_COUNT = "numero";
}
