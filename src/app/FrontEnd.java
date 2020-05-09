package app;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

//this class will call all the methods necessary to make the app run

public class FrontEnd extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Pane root = new Pane();
        Canvas canvas = new Canvas(540, 960);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //Create and display scene
        Scene gameRunning = new Scene(root);
        //Scene mainMenu = new Scene(root);
        //Scene scoreScreen = new Scene(root);
        primaryStage.setTitle("Space Game");
        primaryStage.setScene(gameRunning);

        ArrayList<String> input = new ArrayList<>();
        gameRunning.setOnKeyPressed(e -> { //obtain input from user
            String code = e.getCode().toString();
            if (!input.contains(code)) //only add input if one doesn't already exist
                input.add(code);
        });
        gameRunning.setOnKeyReleased(e -> { //clear the input when key is not being pressed
            String code = e.getCode().toString();
            input.remove(code);
        });
        final long startTime = System.nanoTime();
        try {
            game.startLevel(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        new AnimationTimer()//game time
        {
            private long lastUpdate = 0;
            public void handle(long currentTime){
                long elapsedTime = currentTime - startTime;
                int elapsedTimeInt = Math.toIntExact(elapsedTime / 10000000);
                if (currentTime - lastUpdate >= 16_670_000) {
                    try {
                        System.out.println(elapsedTimeInt);
                        game.logic(gc, elapsedTimeInt, input);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}