package app;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//this class will call all the methods necessary to make the app run
public class FrontEnd extends Application {
    private Scene menuScene, scoreScene, gameScene;
    private boolean gameStart = false;
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Game");
        //main menu button actions
        EventHandler<ActionEvent> startGame = e -> {
            gameStart = true;
            primaryStage.setScene(gameScene);
        };
        //main menu
        Label menuLabel = new Label("Main Menu");
        Button toGame = new Button("Start game");
        toGame.setOnAction(startGame);
        VBox menuBox = new VBox(20);
        menuBox.getChildren().addAll(menuLabel, toGame);
        menuScene = new Scene(menuBox, 300, 250);

        //game scene
        Pane gamePane = new Pane();
        Canvas canvas = new Canvas(540, 960);
        gamePane.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gameScene = new Scene(gamePane);

        //score screen
        Label scoreMenu = new Label("High Scores");
        Button toMenu = new Button("Main Menu");
        toMenu.setOnAction(e->primaryStage.setScene(menuScene));
        VBox scoreBox = new VBox(20);
        scoreBox.getChildren().addAll(scoreMenu, toMenu);
        scoreScene = new Scene(scoreBox, 540, 960);

        //
        primaryStage.setScene(menuScene);

        ArrayList<String> input = new ArrayList<>();
        gameScene.setOnKeyPressed(e -> { //obtain input from user
            String code = e.getCode().toString();
            if (!input.contains(code)) //only add input if one doesn't already exist
                input.add(code);
        });
        gameScene.setOnKeyReleased(e -> { //clear the input when key is not being pressed
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

            public void handle(long currentTime) {
                long elapsedTime = currentTime - startTime;
                int elapsedFrames = Math.toIntExact(elapsedTime / 16_670_000);
                if (currentTime - lastUpdate >= 16_670_000 && gameStart) {
                    try {
                        game.logic(gc, elapsedFrames, input);
                        gameStart = game.running();
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

