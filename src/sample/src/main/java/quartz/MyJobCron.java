package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class MyJobCron implements Job {

    public MyJobCron() {

    }

    @Override
    public void execute(JobExecutionContext arg0) {
        System.out.println("Hello job Cron");
    }
}
