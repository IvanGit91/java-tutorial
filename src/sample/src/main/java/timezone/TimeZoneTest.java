package timezone;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeZoneTest {
    public static void main(String[] args) {
        Locale locale = Locale.getDefault();
        TimeZone localTimeZone = TimeZone.getDefault();
        DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale);
        dateFormat.setTimeZone(localTimeZone);
        Date rightNow = new Date();
        System.out.println(locale.toString() + ": " + dateFormat.format(rightNow));
    }
}
