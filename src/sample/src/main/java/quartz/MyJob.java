package quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class MyJob implements Job {

    public MyJob() {

    }

    @Override
    public void execute(JobExecutionContext arg0) {
        System.out.println("Hello job");
    }
}
