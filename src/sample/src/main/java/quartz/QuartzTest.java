package quartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import utility.Const;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class QuartzTest {

    public static void main(String[] args) {


        // Grab the Scheduler instance from the Factory
        Scheduler scheduler;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            // Simple JOB
            // define the job and tie it to our MyJob class
            JobDetail job = newJob(MyJob.class)
                    .withIdentity("job1", "group1")
                    .build();

            // Trigger the job to run now, and then repeat every 40 seconds
            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()  //Inizia subito, cio� il primo avvio
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(2)
                            .repeatForever())
                    .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);


            // JOB with parameters
            // define the job and tie it to our MyJob class
            JobDetail job2 = newJob(ColorJob.class)
                    .withIdentity("job2", "group1")
                    .build();

            job2.getJobDataMap().put(Const.FAVORITE_COLOR, "Green");
            job2.getJobDataMap().put(Const.EXECUTION_COUNT, 1);

            // Trigger the job to run now, and then repeat every 40 seconds
            Trigger trigger2 = newTrigger()
                    .withIdentity("trigger2", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(10)
                            .repeatForever())
                    .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job2, trigger2);


            // JOB CRON
            // define the job and tie it to our MyJob class
            JobDetail job3 = newJob(MyJobCron.class)
                    .withIdentity("job3", "group1")
                    .build();

            // Trigger the job to run now, and then repeat every 40 seconds
            Trigger trigger3 = newTrigger()
                    .withIdentity("trigger3", "group1")
                    .withSchedule(cronSchedule("0 0/2 8-17 * * ?")) // Build a trigger that will fire every other minute, between 8am and 5pm, every day:
                    .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job3, trigger3);


            // and start it off
            scheduler.start();

            try {
                Thread.sleep(60L * 1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            scheduler.shutdown(true);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }


    }

}
