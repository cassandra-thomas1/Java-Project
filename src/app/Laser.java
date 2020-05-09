package app;

import java.io.IOException;


public class Laser extends Sprite{

    public Laser(double xIn, double yIn, double widthIn, double heightIn, String FileName) throws IOException {
        super(xIn, yIn, widthIn, heightIn, FileName);
    }

    //pre: takes in a double representing y-axis number
    //post: if alive, player moves in the negative Y direction
    @Override
    void move(double rate) {
        if(isAlive()) {
            setYCoord(getY() - rate);
        }
    }
}