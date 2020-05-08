package app;

import java.io.IOException;


public class Laser extends Sprite{

    public Laser(double xIn, double yIn, double widthIn, double heightIn, String FileName) throws IOException {
        super(xIn, yIn, widthIn, heightIn, FileName);
    }

    @Override
    void move(double rate) {
        setYCoord(getY() - rate);       //lasers move in the negative Y direction
    }
}
