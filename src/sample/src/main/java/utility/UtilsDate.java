package utility;

import lombok.SneakyThrows;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Timestamp;
import java.text.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class UtilsDate {

    private static final Calendar CAL = Calendar.getInstance(TimeZone.getDefault());
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

    public static String currentFormattedTimestamp() {
        return LocalDateTime.now(Const.ZONE_ROME).format(Const.EUROPEAN_DATE_FORMATTER);
    }

    public static Date nowPlusSeconds(int seconds) {
        return nowPlusSeconds(new Date(), seconds);
    }

    public static Date nowPlusSeconds(Date date, int seconds) {
        CAL.setTime(date);
        CAL.add(Calendar.SECOND, seconds);
        return CAL.getTime();
    }

    public static Date dateMinusHours(Date date, int hours) {
        CAL.setTime(date);
        CAL.add(Calendar.HOUR_OF_DAY, -hours);
        return CAL.getTime();
    }

    public static Date toDate(LocalDateTime date) {
        return Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date toDate(XMLGregorianCalendar calendar) {
        return calendar == null ? null : calendar.toGregorianCalendar().getTime();
    }

    public static Date toDate(int year, int month, int day, int hour, int minute, int second) {
        CAL.set(year, month, day, hour, minute, second);
        return CAL.getTime();
    }

    public static Date toDateRome(LocalDateTime date) {
        return Date.from(date.atZone(ZoneId.of(Const.TIMEZONE_CEST)).toInstant());
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDate toLocalDate(Date date) {
        return LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    public static LocalDateTime utcToCet(LocalDateTime timeInUtc) {
        ZonedDateTime utcTimeZoned = ZonedDateTime.of(timeInUtc, ZoneId.of("UTC"));
        return utcTimeZoned.withZoneSameInstant(ZoneId.of("CET")).toLocalDateTime();
    }

    public static int timeZoneDifferenceCestUtc() {
        return timeZoneDifference(Const.TIMEZONE_CEST, "UTC");
    }

    public static int timeZoneDifference(String time1, String time2) {
        long ade = System.currentTimeMillis();
        long diff = TimeZone.getTimeZone(time1).getOffset(ade) - TimeZone.getTimeZone(time2).getOffset(ade);
        return (int) TimeUnit.MILLISECONDS.toHours(diff);
    }

    public static Date utcToCetDate(Date time) {
        return toDate(utcToCetLocal(time));
    }

    public static Date utcToCetDate(LocalDateTime time) {
        return toDate(utcToCetLocal(time));
    }

    public static LocalDateTime utcToCetLocal(Date time) {
        return utcToCetLocal(toLocalDateTime(time));
    }

    public static LocalDateTime utcToCetLocal(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault())
                .withZoneSameInstant(ZoneId.of(Const.TIMEZONE_CEST))
                .toLocalDateTime();
    }

    public static Date cetToUtcDate(Date time) {
        return toDate(cetToUtcLocal(time));
    }

    public static Date cetToUtcDate(LocalDateTime time) {
        return toDate(cetToUtcLocal(time));
    }

    public static LocalDateTime cetToUtcLocal(Date time) {
        return cetToUtcLocal(toLocalDateTime(time));
    }

    public static LocalDateTime cetToUtcLocal(LocalDateTime time) {
        return time.atZone(Const.ZONE_ROME)
                .withZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
    }

    public static Date changeTimezoneDate(LocalDateTime time, ZoneId from, ZoneId to) {
        return toDate(changeTimezoneLocal(time, from, to));
    }

    public static LocalDateTime changeTimezoneLocal(LocalDateTime time, final ZoneId from, final ZoneId to) {
        return time.atZone(from)
                .withZoneSameInstant(to)
                .toLocalDateTime();
    }


    /**
     * @param date     Date to edit
     * @param keyToSum What must be added (Calendar.DAY_OF_YEAR for days) (Calendar.MONTH for months) (Calendar.YEAR for years)
     * @param value    The value that must be summed to the date
     * @return The summed date
     */
    public static Date addToDate(Date date, Integer keyToSum, Integer value) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(keyToSum, value);
        return cal.getTime();
    }

    public static Integer getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getYear(cal);
    }

    public static Integer getYear(Calendar cal) {
        return cal.get(Calendar.YEAR);
    }

    public static Integer getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getYear(cal);
    }

    public static Integer getMonth(Calendar cal) {
        return cal.get(Calendar.MONTH);
    }

    public static Integer getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getYear(cal);
    }

    public static Integer getDay(Calendar cal) {
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static long daysElapsed(Calendar inizio, Calendar fine) {
        return daysElapsed(inizio, fine, false, false);
    }

    public static long daysElapsed(Calendar inizio, Calendar fine, boolean checkZero, boolean includeEndDate) {
        long diffInMillies = fine.getTimeInMillis() - inizio.getTimeInMillis();
        if (checkZero && diffInMillies < 0)
            return 0L;
        return TimeUnit.DAYS.convert(Math.abs(diffInMillies), TimeUnit.MILLISECONDS) + ((includeEndDate) ? 1 : 0);
        //return diffInMillies/Const.millisecondsInADay;
    }

    public static int monthsBetween(Calendar i_calendar, Calendar f_calendar) {
        if (i_calendar == null || f_calendar == null) {
            return -1; //Error
        }
        int nMonth1 = 12 * i_calendar.get(Calendar.YEAR) + i_calendar.get(Calendar.MONTH);
        int nMonth2 = 12 * f_calendar.get(Calendar.YEAR) + f_calendar.get(Calendar.MONTH);
        return Math.abs(nMonth2 - nMonth1);
    }

    public static int monthsBetween(Date d1, Date d2) {
        if (d2 == null || d1 == null) {
            return -1; //Error
        }
        Calendar m_calendar = Calendar.getInstance();
        m_calendar.setTime(d1);
        int nMonth1 = 12 * m_calendar.get(Calendar.YEAR) + m_calendar.get(Calendar.MONTH);
        m_calendar.setTime(d2);
        int nMonth2 = 12 * m_calendar.get(Calendar.YEAR) + m_calendar.get(Calendar.MONTH);
        return Math.abs(nMonth2 - nMonth1);
    }

    public static int yearCalendars(Calendar i, Calendar e) {
        e.add(Calendar.DAY_OF_MONTH, -i.get(Calendar.DAY_OF_MONTH));
        e.add(Calendar.MONTH, -i.get(Calendar.MONTH));
        e.add(Calendar.YEAR, -i.get(Calendar.YEAR));
        return e.get(Calendar.YEAR);
    }

    public static int getLastDayOfMonth(Calendar c) {
        return c.getActualMaximum(Calendar.DATE);
    }

    // OLD
    public static String formattaData(Date data) {
        String format = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(format);
        String ret = null;
        if (data != null)
            ret = df.format(data.getTime());
        return ret;
    }

    public static String formattaData(Date data, String formato) {
        String format = formato;
        DateFormat df = new SimpleDateFormat(format);
        String ret = null;
        if (data != null)
            ret = df.format(data.getTime());
        return ret;
    }

    public static String getAnno(Date data) {
        String format = "yyyy";
        DateFormat df = new SimpleDateFormat(format);
        String ret = null;
        if (data != null)
            ret = df.format(data.getTime());
        return ret;
    }

    public static String getMese(Date data) {
        String format = "MM";
        DateFormat df = new SimpleDateFormat(format);
        String ret = null;
        if (data != null)
            ret = df.format(data.getTime());
        return ret;
    }

    public static String formattaOrario(Date data) {
        String format = "HH:mm";
        DateFormat df = new SimpleDateFormat(format);
        String ret = null;
        if (data != null)
            ret = df.format(data.getTime());
        return ret;
    }

    /**
     * Restituisce una stringa con l'orario formattato fino ai secondi
     *
     * @param data
     * @return
     */
    public static String getOrario(Date data) {
        String format = "HH:mm:ss";
        DateFormat df = new SimpleDateFormat(format);
        String ret = null;
        if (data != null)
            ret = df.format(data.getTime());
        return ret;
    }

    public static String convertMillis(long milliseconds) {
        long secTotal = 0;
        long seconds = 0;
        long minutes = 0;
        long hours = 0;
        secTotal = milliseconds / 1000;
        hours = secTotal / 3600;
        secTotal = secTotal - (hours * 3600);
        minutes = secTotal / 60;
        secTotal = secTotal - (minutes * 60);
        seconds = secTotal;
        return (lpad(String.valueOf(hours), 2, "0") + ":" + lpad(String.valueOf(minutes), 2, "0") + ":" + lpad(String.valueOf(seconds), 2, "0"));
    }

    public static Timestamp convertMillisToTimestamp(long milliseconds) throws ParseException {
        String orario = convertMillis(milliseconds);
        String data = formattaData(new Date());
        return (stringToTimestamp(data + " " + orario));
    }


    public static String rpad(String str, int len, String c) {
        String s = (str == null ? "" : str);
        while (s.length() < len)
            s += c;
        return s;
    }

    public static String lpad(String str, int len, String c) {
        String s = (str == null ? "" : str);
        while (s.length() < len)
            s = c + s;
        return s;
    }

    public static String formattaDecimalbox(Double cifra) {
        String cifraFormattata = null;
        if (cifra == null) {
            cifra = 0.00;
        }
        NumberFormat appo = new DecimalFormat("0.00");
        cifraFormattata = appo.format(cifra.doubleValue());

        return cifraFormattata;
    }

    public static int getDatesDiffInMinutes(String strDate1, String strDate2) {
        int minutes = 0;
        long millisDiff = 0;
        try {
            //String strDate1 = "2007/04/15 12:35:05";
            //String strDate2 = "2009/08/02 20:45:07";

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            fmt.setLenient(false);

            // Parses the two strings.
            Date d1 = fmt.parse(strDate1);
            Date d2 = fmt.parse(strDate2);

            // Calculates the difference in milliseconds.
            millisDiff = d2.getTime() - d1.getTime();

            // Calculates days/hours/minutes/seconds.
            int seconds = (int) (millisDiff / 1000 % 60);
            minutes = (int) (millisDiff / 60000 % 60);
            int hours = (int) (millisDiff / 3600000 % 24);
            int days = (int) (millisDiff / 86400000);
			/*
		            System.out.println("Between " + strDate1 + " and " + strDate2 + " there are:");
		            System.out.print(days + " days, ");
		            System.out.print(hours + " hours, ");
		            System.out.print(minutes + " minutes, ");
		            System.out.println(seconds + " seconds");
			 */
        } catch (Exception e) {
            System.err.println(e);
        }
        return Long.valueOf(millisDiff).intValue();
    }

    @SneakyThrows
    public static Date toDate(Object data) {
        return DATE_FORMAT.parse(data.toString());
    }

    public static Timestamp stringToTimestamp(String data) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date parsedDate = df.parse(data);
        return new Timestamp(parsedDate.getTime());
    }

    public static Timestamp addTimeBySecondsDemo(Timestamp date, int millsec) {
        //System.out.println("Given date:"+date);
        Calendar calender = Calendar.getInstance();
        calender.setTimeInMillis(date.getTime());
        calender.add(Calendar.MILLISECOND, millsec);
        Date changeDate = calender.getTime();
        //System.out.println("changeDate ..:"+formattaData(changeDate, "dd/MM/yyyy HH:mm"));

        return new Timestamp(changeDate.getTime());
    }

    public static String todayToString() {
        Date date = new Date();
        return Const.JSF_FORMAT.format(date);
    }

    @SneakyThrows
    public static XMLGregorianCalendar dateToXmlGregorian(Date date) {
        XMLGregorianCalendar xmlDate = null;
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        xmlDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        return xmlDate;
    }

    // MENO UTILI
    public static String convertCheckDateAnno(Date date) {
        return convertCheckDateAnno(date, "");
    }

    public static String convertCheckDate(Date date) {
        return convertCheckDate(date, "");
    }

    public static String convertCheckDate(Date date, SimpleDateFormat dateFormat) {
        return convertCheckDate(date, "", dateFormat);
    }

    public static String convertCheckDate(Date date, String retValue) {
        return convertCheckDate(date, retValue, Const.JSF_FORMAT);
    }

    public static String convertCheckDate(Date date, String retValue, SimpleDateFormat dateFormat) {
        return date != null ? dateFormat.format(date) : retValue;
    }

    public static String convertCheckDateAnno(Date date, String retValue) {
        return date != null ? Const.FORMAT_YEAR.format(date) : retValue;
    }

    public static Long timeDifference(LocalDateTime startDate, LocalDateTime endDate) {
        Duration duration = Duration.between(startDate, endDate);
        return Math.abs(duration.toMinutes());
    }

    public static Integer timeDifference(LocalDate startDate, LocalDate endDate) {
        Period period = Period.between(startDate, endDate);
        return Math.abs(period.getDays());
    }

    public static Long timeDifference(Date startDate, Date endDate) {
        return timeDifference(startDate, endDate, TimeUnit.DAYS);
    }

    public static Long timeDifference(Date startDate, Date endDate, TimeUnit timeUnit) {
        long diffInMillies = Math.abs(endDate.getTime() - startDate.getTime());
        return timeUnit.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }

    public static Date plusMinutesToDate(Date date, long amount) {
        return plusTimesToDate(date, amount, ChronoUnit.MINUTES);
    }

    public static Date plusDaysToDate(Date date, long amount) {
        return plusTimesToDate(date, amount, ChronoUnit.DAYS);
    }

    public static Date plusTimesToDate(Date date, long amount, ChronoUnit chronoUnit) {
        return Date.from(date.toInstant().plus(amount, chronoUnit));
    }

    public static Long timeElapsedInSeconds(LocalDateTime start, LocalDateTime end) {
        return timeElapsed(start, end, ChronoUnit.SECONDS);
    }

    public static Long timeElapsed(LocalDateTime start, LocalDateTime end, ChronoUnit unit) {
        return start.until(end, unit);
    }

}
