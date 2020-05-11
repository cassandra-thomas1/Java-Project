package app;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

//this class will call all the methods necessary to make the app run
//hhhahahha
public class FrontEnd extends Application {
    private String playerName;
    private Scene menuScene, scoreScene, gameScene, nameScene;
    private boolean gameStart = false;
    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("Space Shooter");
        //main menu button actions
        EventHandler<ActionEvent> startGame = e -> {
            gameStart = true;
            primaryStage.setScene(gameScene);
            try {
                game.startLevel();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
        //menu Background
        ImageView imageView = new ImageView(new Image(new FileInputStream("Resources\\background.png")));
        ImageView imageView2 = new ImageView(new Image(new FileInputStream("Resources\\background.png")));

        Label namePls = new Label("Name: ");
        namePls.setStyle("-fx-text-fill: #00ffbb;");
        Button toGame = new Button("Start game");
        Button toScore = new Button("High scores");
        TextField nameEntry = new TextField();
        toGame.setOnAction(startGame);
        toScore.setOnAction(e->primaryStage.setScene(scoreScene));
        VBox menuOptions = new VBox();
        menuOptions.getChildren().addAll(namePls, nameEntry, toGame, toScore);
        menuOptions.setMaxWidth(200);
        menuOptions.setMaxHeight(100);
        StackPane menuBox = new StackPane();
        StackPane.setAlignment(menuBox, Pos.CENTER);
        menuBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        menuBox.getChildren().addAll(imageView, menuOptions);
        menuScene = new Scene(menuBox, 540, 960);

        //game scene
        Pane gamePane = new Pane();
        Canvas canvas = new Canvas(540, 960);
        gamePane.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gameScene = new Scene(gamePane);

        //score screen
        Label scoreMenu = new Label("High Scores");
        scoreMenu.setStyle("-fx-text-fill: #00ffbb;");
        Button toMenu = new Button("Main Menu");
        toMenu.setOnAction(e->primaryStage.setScene(menuScene));
        VBox scoreOptions = new VBox();
        scoreOptions.getChildren().addAll(scoreMenu, toMenu);
        scoreOptions.setMaxWidth(200);
        scoreOptions.setMaxHeight(100);
        StackPane scoreBox = new StackPane();
        StackPane.setAlignment(scoreBox, Pos.CENTER);
        scoreBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        scoreBox.getChildren().addAll(imageView2, scoreOptions);
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
            new AnimationTimer()//game time
            {
                public void handle(long currentTime) {
                    long elapsedTime = currentTime - startTime;
                    int elapsedFrames = Math.toIntExact(elapsedTime / 16_670_000);
                    long lastUpdate = 0;
                    if (currentTime - lastUpdate >= 16_670_000 && gameStart) {
                        try {
                            game.logic(gc, elapsedFrames, input);
                            if (!game.running()){
                                gameStart = false;
                                primaryStage.setScene(scoreScene);
                            }
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