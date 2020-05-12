package app;

import java.io.IOException;

public class ScoreCounter extends MovingText {

    //constructor initializes the position of the text and the score as 0
    public ScoreCounter(double x, double y, long input) throws IOException {
        super(x, y);
        updateText(input);
    }

    //pre:  the score needs to be updated
    //post: calls the super class
    public void updateText(long input) {
        super.updateText("Score: " + input);
    }
}
