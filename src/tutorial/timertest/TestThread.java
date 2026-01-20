package tutorial.timertest;

import java.util.Timer;
import java.util.TimerTask;

public class TestThread {

    static int counter = 0;

    public static void main(String[] args) {

        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                System.out.println("TimerTask executing counter is: " + counter);
                counter++; //increments the counter
            }
        };

        Timer timer = new Timer("MyTimer"); //create a new Timer

        // 30 times every 3 seconds
        //timer.scheduleAtFixedRate(timerTask, 1, 3000); //this line starts the timer at the same time its executed
        timer.schedule(timerTask, 3000);
        timer.schedule(getTimerTask(), 5000);

    }

    public static TimerTask getTimerTask() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                System.out.println("TimerTask executing counter isssss: " + counter);
                counter++; //increments the counter
            }
        };
        return timerTask;
    }
}
