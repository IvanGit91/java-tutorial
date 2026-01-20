package tutorial.date;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class TDateTimezone {

    private static final String CUSTOM_FORMAT_STRING0 = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    private static final SimpleDateFormat ISO_FORMATTER = new SimpleDateFormat(CUSTOM_FORMAT_STRING0);
    private static Integer count = 0;

    public static void main(String[] args) {
        formatLocalDateTime();
        formatDate();
        formatZonedDateTime();
        parseZonedDateTime();
    }

    public static void formatLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        logger(now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)); // 2021-03-10T12:59:35.4678299
    }

    public static void formatDate() {
        Date now2 = new Date();
        logger(ISO_FORMATTER.format(now2));  //2021-03-10T12:59:35.493+01:00  (+01:00 indicate that the time is +1 rispetto ad UTC, that is in CEST)
        ISO_FORMATTER.setTimeZone(TimeZone.getTimeZone("UTC"));
        logger(ISO_FORMATTER.format(now2));   //2021-03-10T11:59:35.493Z
    }

    public static void formatZonedDateTime() {
        String date = "2021-03-10T16:06:00+01:00";
        LocalDateTime ldate = LocalDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);


        logger(ldate.atZone(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)); // 2021-03-10T16:06:00
        logger(ldate.atZone(ZoneId.of("Europe/Rome")).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));  //2021-03-10T16:06:00

        ZonedDateTime zDate = ZonedDateTime.parse(date, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        logger(zDate.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME));  //2021-03-10T16:06:00+01:00
        logger(zDate.withZoneSameInstant(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)); //2021-03-10T15:06:00Z
        logger(zDate.withZoneSameInstant(ZoneId.of("CET")).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)); //2021-03-10T16:06:00+01:00


        Date zonDate = Date.from(zDate.toInstant());
        logger(zonDate);  //Wed Mar 10 16:06:00 CET 2021
    }

    public static void parseZonedDateTime() {
        ZonedDateTime dateTimeZoned = ZonedDateTime.ofInstant(new Date().toInstant(), ZoneId.of("CET"));
        String strZoned = dateTimeZoned.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        logger(strZoned);  //2021-03-10T13:31:24.65+01:00
    }

    public static void logger(Object msg) {
        System.out.println("<" + ++count + ">: " + msg);
    }

}
