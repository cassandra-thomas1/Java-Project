package app;

import java.io.IOException;

public class Player extends Sprite{
    public Player(double xIn, double yIn, double widthIn, double heightIn, String FileName) throws IOException {
        super(xIn, yIn, widthIn, heightIn, FileName);
    }

    //pre: takes in a double representing x-axis number
    //post: if alive, player moves across x-axis
    @Override
    void move(double rate) {
        if(isAlive()) {
            setXCoord(getX() + rate);
        }
    }
}
