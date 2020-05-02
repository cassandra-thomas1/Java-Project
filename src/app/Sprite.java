package app;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.io.IOException;
import java.net.URL;

public class Sprite {
    private Image image;
    private double xCoord;
    private double yCoord;
    private double width;
    private double height;
    private Rectangle rectangle;
    private boolean alive = true;
    public Sprite(double xIn, double yIn, double widthIn, double heightIn, String URLname) throws IOException {
        setPosition(xIn, yIn);
        setDimensions(widthIn, heightIn);
        image = new Image(new URL(URLname).openStream());
        rectangle = new Rectangle(xIn, yIn, widthIn, heightIn);
        rectangle.setFill(Color.RED);
    }
    public Sprite(double xIn, double yIn, String URLname) throws IOException {
        setPosition(xIn, yIn);
        image = new Image(new URL(URLname).openStream());
    }
    public boolean intersects(Sprite spriteIn)
    {
        return spriteIn.rectangle.intersects(rectangle.getBoundsInLocal());
    }
    public void setPosition(double xIn, double yIn)
    {
        xCoord = xIn;
        yCoord = yIn;
    }
    public void setDimensions(double widthIn, double heightIn)
    {
        width = widthIn;
        height = heightIn;
    }
    public void move(double xIn, double yIn){
        xCoord += xIn;
        yCoord += yIn;
        rectangle.setX(xCoord);
        rectangle.setY(yCoord);
    }
    public void render(GraphicsContext gc){
        gc.drawImage(image, xCoord, yCoord);
    }
    public void setImage(String URLname) throws IOException {
        image = new Image(new URL(URLname).openStream());
    }
    public boolean isAlive(){
        return alive;
    }
    public void kill(){
        alive = false;
    }
    public double getX(){return xCoord;}
    public double getY(){return yCoord;}
    public double getHeight(){return height;}
    public double getWidth(){return width;}
}
