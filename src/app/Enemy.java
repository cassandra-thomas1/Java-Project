package app;

import java.io.IOException;
//this class represents the  enemy ships that the player fights
public class Enemy extends Sprite {
    public Enemy(double xIn, double yIn, double widthIn, double heightIn, String FileName) throws IOException {
        super(xIn, yIn, widthIn, heightIn, FileName);
    }

    //pre: takes in a double representing y-axis number
    //post: if alive, player moves in the positive Y direction
    @Override
    void move(double rate) {
        if(isAlive()) {
            setYCoord(getY() + rate);
        }
    }
}