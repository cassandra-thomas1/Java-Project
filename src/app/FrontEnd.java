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
        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);

        ArrayList<String> input = new ArrayList<>();

        scene.setOnKeyPressed(e -> { //obtain input from user
                    String code = e.getCode().toString();
                    if ( !input.contains(code) )
                        input.add(code);
                });
        scene.setOnKeyReleased(e -> { //clear the input when key is not being pressed
                    String code = e.getCode().toString();
                    input.remove( code );
                });

        //loading assets
        //pink background - https://wizardlyshoe4.s-ul.eu/JavaProject/wMSF3G0R
        //blue background - https://wizardlyshoe4.s-ul.eu/JavaProject/GqiZaxYz
        //red background - https://wizardlyshoe4.s-ul.eu/JavaProject/7gqo31wO
        Image ship = new Image(new URL("https://wizardlyshoe4.s-ul.eu/JavaProject/PcY9pXSJ").openStream());
        Image enemyShip = new Image(new URL("https://wizardlyshoe4.s-ul.eu/JavaProject/6ayA0vm0").openStream());
        Image backGround = new Image(new URL("https://wizardlyshoe4.s-ul.eu/JavaProject/wMSF3G0R").openStream());

        //final long startNanoTime = System.nanoTime();
        final int[] itemCoords= {240, -4096};//value 1 is ship starting position, value 2 is background starting position
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                //double t = (currentNanoTime - startNanoTime) / 1000000000.0;//time variable that we can use to move enemy ships and stuff
                if (itemCoords[1] == 0)
                    itemCoords[1] = -4096;
                itemCoords[1] += 1;
                if (input.contains("LEFT") && itemCoords[0] > 0)
                    itemCoords[0] -= 3;
                else if (input.contains("RIGHT") && itemCoords[0] < 468)
                    itemCoords[0] += 3;
                //if (input.contains("SPACE"))
                    //spawn the bullet and move it forward
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