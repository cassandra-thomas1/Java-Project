package app;

import java.io.IOException;

public class Enemy extends Sprite {
    public Enemy(double xIn, double yIn, double widthIn, double heightIn, String FileName) throws IOException {
        super(xIn, yIn, widthIn, heightIn, FileName);
    }

    @Override
    void move(double rate) {
        setYCoord(getY() + rate);       //Enemy ships move in the positive Y direction
    }
}
