package tutorial.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class TDate {

    private static final String CUSTOM_FORMAT_STRING1 = "yyyy-MM-dd'T'HH:mm:ssZ";
    private static final String CUSTOM_FORMAT_STRING2 = "EEE MMM dd hh:mm:ss ZZZZ yyyy";

    public static void main(String[] args) {
        //testDate();
        //testDate2();
    }

    public static void testDate2() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        LocalDateTime date = LocalDateTime.now();
        Date now = Date.from(date.atZone(ZoneId.of("Europe/Rome")).toInstant());
        System.out.println("CEST: " + now);
        now = Date.from(date.atZone(ZoneId.of("UTC")).toInstant());
        System.out.println("UTC: " + now);


        long ade = System.currentTimeMillis();
        long diff = TimeZone.getTimeZone("Europe/Rome").getOffset(ade) - TimeZone.getTimeZone("UTC").getOffset(ade);
        System.out.println("diff: " + diff);
        long ok = TimeUnit.MILLISECONDS.toHours(diff);
        System.out.println("ok: " + ok);
    }

    public static void testDate() throws ParseException {
        String data = "2020-10-16T12:10:00-0200";

        Instant instant = Instant.parse(data);
        ZonedDateTime z = instant.atZone(ZoneId.systemDefault());
        Date oye = Date.from(z.toInstant());
        System.out.println("Ecco1: " + oye);

        SimpleDateFormat s = new SimpleDateFormat(CUSTOM_FORMAT_STRING2);
        Date date = s.parse(data);
        System.out.println("Ecco: " + date);
        SimpleDateFormat s2 = new SimpleDateFormat(CUSTOM_FORMAT_STRING1);
    }
}
