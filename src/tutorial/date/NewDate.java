package tutorial.date;

import java.time.*;

public class NewDate {

    public static void main(String[] args) {

        // Get the local date
        final LocalDate date = LocalDate.now();

        // Get the local time
        final LocalTime time = LocalTime.now();

        // Get the local date/time
        final LocalDateTime datetime = LocalDateTime.now();

        System.out.println("TIME: " + datetime);

        // Get duration between two dates
        final LocalDateTime from = LocalDateTime.of(2014, Month.APRIL, 16, 0, 0, 0);
        final LocalDateTime to = LocalDateTime.of(2015, Month.APRIL, 16, 23, 59, 59);

        final Duration duration = Duration.between(from, to);
    }
}
