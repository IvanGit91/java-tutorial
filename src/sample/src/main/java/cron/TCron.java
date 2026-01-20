package cron;

import org.springframework.scheduling.support.CronExpression;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TCron {

    public static void main(String[] args) {
        //testCron("*/5 * * * * *"); // every 5 seconds
        testCron("0 0 5 * * ?"); // every day at 05:00
        //testCron("0 0 0 1,16 * ?"); // the first and the 16 of the month
        //testCron("0 0 9,13 * * *");
        //testCron("0 0 7 * * ?"); // Every day at 07:00
        //testCron("0 40 11 * * ?"); // Every day at 11:40
        //testCron("0 */15 * * * *"); // Every 15 minutes
    }

    public static void testCron(String cronExpr) {
        CronExpression expression = CronExpression.parse(cronExpr);
        LocalDateTime now = LocalDateTime.now();
        System.out.println("now time:\t" + now);

        // Get next execution time from tomorrow
        LocalDateTime tomorrow = now.plusDays(1);
        LocalDateTime next = expression.next(tomorrow);
        System.out.println("next run time:\t" + next);

        // If you need a Date object:
        if (next != null) {
            Date nextDate = Date.from(next.atZone(ZoneId.systemDefault()).toInstant());
            System.out.println("next run (Date):\t" + nextDate);
        }
    }

}
