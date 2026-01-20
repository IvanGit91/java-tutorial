package tutorial.timestamp;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestCalendar {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public static void main(String[] args) {
        try {
            String string1 = "20:11:13";
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(string1);

            System.out.println("Date1: " + time1);

            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            String string2 = "14:49:00";
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(string2);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);

            String someRandomTime = "01:00:00";
            Date d = new SimpleDateFormat("HH:mm:ss").parse(someRandomTime);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                //checks whether the current time is between 14:49:00 and 20:11:13.
                System.out.println(true);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String string1 = sdf.format(timestamp);
            Date time1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(string1);

            System.out.println("Date1: " + time1);

            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            String string2 = "2017/05/04 23:59:59";
            Date time2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(string2);

            System.out.println("Date2: " + time2);

            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);
            calendar2.add(Calendar.DATE, 1);

            String someRandomTime = "2017/05/04 11:49:00";
            Date d = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(someRandomTime);

            System.out.println("Date3: " + d);

            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(d);
            calendar3.add(Calendar.DATE, 1);

            Date x = calendar3.getTime();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {
                //checks whether the current time is between 14:49:00 and 20:11:13.
                System.out.println(true);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
