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


//loading assets
//player ship - https://wizardlyshoe4.s-ul.eu/JavaProject/PcY9pXSJ
//enemy ship - https://wizardlyshoe4.s-ul.eu/JavaProject/6ayA0vm0
//bullet - https://wizardlyshoe4.s-ul.eu/JavaProject/fj4ed1FD
//pink background - https://wizardlyshoe4.s-ul.eu/JavaProject/wMSF3G0R
//blue background - https://wizardlyshoe4.s-ul.eu/JavaProject/ehyt3M7Y
//red background - https://wizardlyshoe4.s-ul.eu/JavaProject/QM2S3Ttk

public class FrontEnd extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        Pane root = new Pane();
        Canvas canvas = new Canvas(540, 960);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //Create and display scene
        Scene scene = new Scene(root);
        primaryStage.setTitle("Space Game");
        primaryStage.setScene(scene);

        ArrayList<String> input = new ArrayList<>();
        scene.setOnKeyPressed(e -> { //obtain input from user
            String code = e.getCode().toString();
            if (!input.contains(code)) //only add input if one doesn't already exist
                input.add(code);
        });
        scene.setOnKeyReleased(e -> { //clear the input when key is not being pressed
            String code = e.getCode().toString();
            input.remove(code);
        });


        final long startTime = System.nanoTime();
        new AnimationTimer()//game time
        {
            public void handle(long currentTime){
                long elapsedTime = currentTime - startTime;
                int elapsedTimeInt = Math.toIntExact(elapsedTime / 1000000000);
                switch(elapsedTimeInt){ //this doesn't work at all how I intended it to, I think instead the enemies should spawn at a fixed interval that increases as the levels get higher
                    case 0:
                        try {
                            game.startLevel(1);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                   /* case 30:
                        try {
                            game.startLevel(2);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 60:
                        try {
                            game.startLevel(3);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 90:
                        try {
                            game.startLevel(4);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                }
                try {
                    game.logic(gc, elapsedTime, input);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}