package app;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public abstract class MovingText {
    private double yPos, xPos;
    private String text;

    //constructor initializes the position of the text
    public MovingText(double x, double y){
        yPos = y;
        xPos = x;
    }

    //pre: value in the text-field needs to be changed
    //post: updates the text-field
    public void updateText(String input){
        text = input;
    }



    //pre: game is running
    //post: displays the current value in the text-field
    public void render(GraphicsContext gc){
        gc.strokeText(text, xPos, yPos);
        gc.setStroke(Color.WHITE);
    }


}
