package app;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public abstract class MovingText {
    private double yPos, xPos;
    private String text;


    public MovingText(double x, double y){
        yPos = y;
        xPos = x;
    }

    public void updateText(String input){
        text = input;
    }

    public void render(GraphicsContext gc){
        gc.strokeText(text, xPos, yPos);
        gc.setStroke(Color.WHITE);
    }


}
