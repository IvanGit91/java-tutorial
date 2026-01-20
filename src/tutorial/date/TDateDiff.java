package tutorial.date;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TDateDiff {

    public static void main(String[] args) {
        LocalDateTime loc = LocalDateTime.now();
        testDate(loc, loc.plusMinutes(10));
        testDate(loc.minusHours(5), loc.plusHours(15).plusMinutes(10));
        testDate(loc.minusHours(5), loc.plusHours(15));
        testDate(loc.minusDays(2), loc.plusHours(15).plusMinutes(10));
        testDate(loc, loc.plusDays(10).plusHours(10).plusMinutes(35));
    }

    public static void testDate(LocalDateTime start, LocalDateTime end) {
        Date startDate = Date.from(start.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
        String f = DaysHourMinutesFormat(startDate, endDate);
        System.out.println("FORMAT: " + f);
    }

    //1g 2h 30m
    public static String DaysHourMinutesFormat(Date startDate, Date endDate) {
        StringBuilder res = new StringBuilder();
        Long g = 0L, h = 0L, m;

        m = timeDifferenceInMinutes(startDate, endDate);
        Double hours = Double.valueOf(m) / 60;
        if (hours >= 1) {

            h = hours.longValue();
            Double remainingMinutes = (hours - hours.intValue()) * 60;
            m = Math.round(remainingMinutes);

            Double days = Double.valueOf(h) / 24;
            if (days >= 1) {
                g = days.longValue();
                Double remainingHours = (days - days.intValue()) * 24;
                h = Math.round(remainingHours);
            }
        }
        if (g != 0L)
            res.append(g).append("g ");
        if (h != 0L)
            res.append(h).append("h ");
        res.append(m).append("m");
        return res.toString();
    }

    public static Long timeDifferenceInMinutes(Date startDate, Date endDate) {
        return timeDifference(startDate, endDate, TimeUnit.MINUTES);
    }

    public static Long timeDifference(Date startDate, Date endDate, TimeUnit timeUnit) {
        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

}
