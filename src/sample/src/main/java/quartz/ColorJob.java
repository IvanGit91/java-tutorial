package quartz;

import org.quartz.*;
import utility.Const;

import java.util.Date;

@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ColorJob implements Job {

    private final int _counter = 1;


    public ColorJob() {

    }

    @Override
    public void execute(JobExecutionContext context) {
        JobDataMap data = context.getJobDetail().getJobDataMap();
        String favoriteColor = data.getString(Const.FAVORITE_COLOR);
        int count = data.getInt(Const.EXECUTION_COUNT);
        System.out.println("ColorJob: " + context.getFireInstanceId() + " executing at " + new Date() + "\n" +
                "  favorite color is " + favoriteColor + "\n" +
                "  execution count (from job map) is " + count + "\n" +
                "  execution count (from job member variable) is " + _counter);
        count++;
        data.put(Const.EXECUTION_COUNT, count);
    }
}
