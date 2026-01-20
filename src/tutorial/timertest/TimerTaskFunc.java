package tutorial.timertest;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskFunc {

    public static void main(String[] args) {
        newTimerTask();
    }


    public static void newTimerTask() {
        Timer oneTimer = new Timer();   //create a new Timer
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("oneTimer");
                this.cancel();
                oneTimer.cancel();  //it stops the timer
            }
        };
        oneTimer.schedule(timerTask, 3000);
    }
}
