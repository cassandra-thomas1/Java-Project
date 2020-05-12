package app;

import java.io.IOException;

public class Timer extends MovingText {
    private long minute;
    private long second;

    //constructor initializes the position of the text and the in-game time at 0
    public Timer(double x, double y, long input) throws IOException {
        super(x, y);
        updateText(input);
        minute = 0;
        second = 0;
    }


    //pre: the time needs to be changed
    //post: converts the time from seconds to minutes + seconds and calls super class
    public void updateText(long input) {
            minute = input / 60;
            second = input % 60;

        if(second < 10){
            super.updateText(minute + ":0" + second);
        }
        else
            super.updateText(minute + ":" + second);
    }
}
