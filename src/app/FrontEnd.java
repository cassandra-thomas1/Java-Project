package app;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class FrontEnd extends Application{
    @Override
    public void start(Stage primaryStage) throws IOException {
        Group root = new Group();
        Canvas canvas = new Canvas (540, 960);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //Create and display scene
        Scene scene = new Scene(root);
        primaryStage.setTitle("Space Game");
        primaryStage.setScene(scene);

        ArrayList<String> input = new ArrayList<>();
        scene.setOnKeyPressed(e -> { //obtain input from user, it's nice to do it this way so that we don't have to make switch/ case statements for every input, we just handle what they inputted at execution
                    String code = e.getCode().toString();
                    if ( !input.contains(code) ) //only add input if one doesn't already exist
                        input.add(code);
                });
        scene.setOnKeyReleased(e -> { //clear the input when key is not being pressed
                    String code = e.getCode().toString();
                    input.remove(code);
                });

        //loading assets
        //pink background - https://wizardlyshoe4.s-ul.eu/JavaProject/wMSF3G0R
        //blue background - https://wizardlyshoe4.s-ul.eu/JavaProject/GqiZaxYz (not optimized or made to loop yet)
        //red background - https://wizardlyshoe4.s-ul.eu/JavaProject/7gqo31wO (not optimized or made to loop yet)
        Image ship = new Image(new URL("https://wizardlyshoe4.s-ul.eu/JavaProject/PcY9pXSJ").openStream());
        Image enemyShip = new Image(new URL("https://wizardlyshoe4.s-ul.eu/JavaProject/6ayA0vm0").openStream());
        Image backGround = new Image(new URL("https://wizardlyshoe4.s-ul.eu/JavaProject/wMSF3G0R").openStream());

        //final long startNanoTime = System.nanoTime();
        final int[] itemCoords= {240, -4096};//value 1 is ship starting position, value 2 is background starting position, I had to do it this way cause intellij told me to
        new AnimationTimer()//this is the game logic/ rendering, we should move this to the game class but I got carried away figuring stuff out tonight
        {
            public void handle(long currentNanoTime)
            {
                //double t = (currentNanoTime - startNanoTime) / 1000000000.0;//time variable that we can use to move enemy ships and stuff
                if (itemCoords[1] == 0)//if the background reaches the end of the image, loop back to the beginning
                    itemCoords[1] = -4096;
                itemCoords[1] += 1;//each frame display the background 1 pixel up
                if (input.contains("LEFT") && itemCoords[0] > 0)
                    itemCoords[0] -= 3; //if the user presses left on the keyboard, move the ship 3 pixels to the left during the current frame
                else if (input.contains("RIGHT") && itemCoords[0] < 468)
                    itemCoords[0] += 3; //if the user presses right on the keyboard, move the ship 3 frames to the right during the current frame
                //if (input.contains("SPACE"))
                    //spawn the bullet, bullet should spawn on the player x coordinate and y coordinate on the frame it spawns, and
                    //maintain the same x coordinate while moving up some number of pixels per frame to determine the speed of the bullet (maybe 10 pixels)
                //as for hit detection i'm fairly certain we'll be able to check every frame if the bullet image coordinates are in the same place
                //(within x amount of pixels to account for the enemy ship hitbox) and if it does we just stop drawing the enemy ship and
                //add whatever amount to the score
                gc.drawImage(backGround, 0, itemCoords[1]);
                gc.drawImage(ship, itemCoords[0], 850);
                gc.drawImage(enemyShip, 240, 0);

            }
        }.start();


        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}