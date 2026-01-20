package tutorial.timertest;


import java.util.Timer;
import java.util.TimerTask;

public class TimerTest {

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

        timer.scheduleAtFixedRate(timerTask, 5000, 6000); //this line starts the timer at the same time its executed
    }
}
