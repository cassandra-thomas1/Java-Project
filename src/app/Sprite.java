package app;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import java.io.FileInputStream;
import java.io.IOException;

public abstract class Sprite {
    private Image image;
    private double xCoord;
    private double yCoord;
    private double width;
    private double height;
    private Rectangle rectangle;
    private boolean alive = true;

    //constructor
    public Sprite(double xIn, double yIn, double widthIn, double heightIn, String FileName) throws IOException {
        xCoord = xIn;
        yCoord = yIn;
        width = widthIn;
        height = heightIn;
        image = new Image(new FileInputStream(FileName));
        rectangle = new Rectangle(xIn, yIn, widthIn, heightIn);
    }

    //pre: 2 objects exist in the same y vicinity
    //post: sees if the objects' hit-box have made contact, if so the sprite is killed
    public boolean intersects(Sprite spriteIn)
    {
        return spriteIn.rectangle.intersects(rectangle.getBoundsInLocal());
    }
        // ^^NEEDS REVISION^^ \\


    //pre: method is called in game
    //post: position is incremented or decremented
    abstract void move(double rate);


    //pre:  sprite is loaded into the game
    //post: displays sprite on the screen
    public void render(GraphicsContext gc){
        gc.drawImage(image, xCoord, yCoord);
    }


    public boolean isAlive(){
        return alive;
    }
    public void kill(){
        alive = false;
    }
    public double getX(){return xCoord;}
    public double getY(){return yCoord;}

    public void setXCoord(double xCoord) { this.xCoord = xCoord; }

    public void setYCoord(double yCoord) { this.yCoord = yCoord; }

    public double getHeight(){return height;}
    public double getWidth(){return width;}
}
