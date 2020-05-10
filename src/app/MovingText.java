package app;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class MovingText {
    private double yPos, xPos;
    private String text;
    GraphicsContext display;

    public MovingText(double y, double x, long input){
        yPos = y;
        xPos = x;
        text = Long.toString(input);

    }

    public void updateText(long input){
        text = Long.toString(input);
    }

    public void render(GraphicsContext gc){
        gc.strokeText(text, xPos, yPos);
        gc.setStroke(Color.WHITE);
    }


}
