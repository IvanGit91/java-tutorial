package tutorial.date;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TestDate {
    private static final TimeZone ceTZ = TimeZone.getTimeZone("Europe/Rome");

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(ceTZ);

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf2.setTimeZone(ceTZ);

        Date now = new Date();

        String data1 = "2018-02-17";
        String data2 = "2017-10-04 14:01:33.478000";

        try {
            String convertedCurrentDate = sdf.format(sdf.parse(data1));
            System.out.println("DATA: " + convertedCurrentDate);
            Date convertedDate = sdf.parse(data1);
            System.out.println("DATA: " + convertedDate);

            String convertedCurrentDate2 = sdf.format(sdf.parse(data2));
            System.out.println("DATA: " + convertedCurrentDate2);
            Date convertedDate2 = sdf.parse(data2);
            System.out.println("DATA: " + convertedDate2);

            System.out.println("FORMAT: " + sdf.format(convertedDate2));

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            String format3 = sdf2.format(now);
            System.out.println("FORMAT3: " + format3);
            System.out.println("FORMAT3PARSE: " + sdf2.parse(format3));

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}