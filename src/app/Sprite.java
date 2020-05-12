package app;
import javafx.geometry.Bounds;
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
        return rectangle.intersects(spriteIn.getBounds());
    }


    //pre: method is called in game
    //post: position is incremented or decremented
    abstract void move(double rate);


    //pre:  sprite is loaded into the game
    //post: displays sprite on the screen
    public void render(GraphicsContext gc){
        if(alive) {
            gc.drawImage(image, xCoord, yCoord);
        }
    }

    //pre: sprite's x position has changed
    //post: changes its xcoord variable with the parameter
    public void setXCoord(double xCoord) {
        this.xCoord = xCoord;
        rectangle.setX(xCoord);
    }

    //pre: sprite's y position has changed
    //post: changes its ycoord variable with the parameter
    public void setYCoord(double yCoord) {
        this.yCoord = yCoord;
        rectangle.setY(yCoord);
    }

    //pre:  game function is chacking the status of a sprite
    //post: returns bool value representing if the player is still alive
    public boolean isAlive(){
        return alive;
    }

    //pre:  a collision has been made and kill has been invoked
    //post: the sprite is no longer alive and it's hitbox is removed from the  game bounds
    public void kill(){
        alive = false;
        rectangle = new Rectangle(-100, -100, 0, 0);
    }
    //pre: game class needs a trait of this class
    //post: returns the value representing the trait desired
    public double getX(){return xCoord;}
    public double getY(){return yCoord;}
    public double getHeight(){return height;}

    //pre: method is invoked
    //post: returns a value representing the coordinate where the hitbox is located
    private Bounds getBounds(){return rectangle.getBoundsInParent();}
}