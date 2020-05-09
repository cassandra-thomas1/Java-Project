package app;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Background {
    private int yPosition;
    private int startingPosition;
    Image image;
    public Background (String fileName) throws FileNotFoundException {
        image = new Image(new FileInputStream(fileName));
    }
    public Background(String fileName, int yResolution) throws FileNotFoundException {
        image = new Image(new FileInputStream(fileName));
        startingPosition = yResolution - 960;
        yPosition = startingPosition;
    }
    public void scroll(){
        if (yPosition == 0)
            yPosition = startingPosition;
        ++yPosition;
    }
    public void render(GraphicsContext gc){
        gc.drawImage(image, 0, yPosition);
    }
}
//if (backGround.getY() == 0)
//    backGround.setPosition(0, -4096);
//backGround.setPosition(0, backGround.getY() + 1);