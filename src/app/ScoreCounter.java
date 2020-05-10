package app;

import java.io.IOException;

public class ScoreCounter extends MovingText {
    public ScoreCounter(double x, double y, long input) throws IOException {
        super(x, y);
        updateText(input);
    }

    public void updateText(long input) {
        super.updateText("Score: " + input);
    }
}
