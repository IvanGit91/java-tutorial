package tutorial.timertest;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTaskFunc2 {

    public static void main(String[] args) {

        newTimerTask("Ciao1", 1000);
        newTimerTask("Ciao", 3000);
        newTimerTask("Ciaone", 6000);
        newTimerTask("Ciao2", 7000);
        newTimerTask("Cia3", 2000);
    }


    public static void newTimerTask(String ciao, Integer time) {
        Timer oneTimer = new Timer();   //create a new Timer
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("oneTimer " + ciao);
                this.cancel();
                oneTimer.cancel();  //it stops the timer
            }
        };
        oneTimer.schedule(timerTask, time);
    }
}
