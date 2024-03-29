package app;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.layout.Background;
import javafx.stage.Stage;

//this class will call all the methods necessary to make the app run
public class FrontEnd extends Application {
    private TableView<Score> table = new TableView<>();
    private String playerName;
    private Scene menuScene, scoreScene, gameScene, nameScene;
    private boolean gameStart = false;
    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        //load in database
        ArrayList<Score> scores = DB.access();
        //initialize the text field for player name entry
        TextField nameEntry = new TextField();
        //event handler for the start game button
        EventHandler<ActionEvent> startGame = e -> {
            gameStart = true;
            playerName = nameEntry.getText();
            primaryStage.setScene(gameScene);
            try {
                game.startLevel();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        };
        EventHandler<ActionEvent> accessScores = e -> {
            //sort score array
            //push score array onto table
            primaryStage.setScene(scoreScene);
        };
        //menu Background
        ImageView imageView = new ImageView(new Image(new FileInputStream("Resources\\background.png")));
        ImageView imageView2 = new ImageView(new Image(new FileInputStream("Resources\\background.png")));

        //main menu buttons and labels
        primaryStage.setTitle("Space Shooter");
        Label namePls = new Label("Name: ");
        namePls.setStyle("-fx-text-fill: #00ffbd; -fx-font-weight: bold; -fx-font-size: 20");
        Button toGame = new Button("Start game");
        Button toScore = new Button("High scores");
        toGame.setOnAction(startGame);
        toScore.setOnAction(e->primaryStage.setScene(scoreScene));
        //main menu vbox to contain all of the elements created above
        VBox menuOptions = new VBox();
        menuOptions.getChildren().addAll(namePls, nameEntry, toGame, toScore);
        menuOptions.setMaxWidth(200);
        menuOptions.setMaxHeight(100);
        menuOptions.setSpacing(10);
        StackPane menuBox = new StackPane();
        StackPane.setAlignment(menuBox, Pos.CENTER);
        menuBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");//transparency property so that we can see the background
        menuBox.getChildren().addAll(imageView, menuOptions);
        menuScene = new Scene(menuBox, 540, 960);

        //game scene
        Pane gamePane = new Pane();
        Canvas canvas = new Canvas(540, 960);
        gamePane.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gameScene = new Scene(gamePane);

        //score screen buttons, labels, and event handlers
        Label scoreMenu = new Label("High Scores");
        scoreMenu.setStyle("-fx-text-fill: #00ffbd; -fx-font-weight: bold; -fx-font-size: 20");
        Button toMenu = new Button("Main Menu");
        toMenu.setOnAction(e->primaryStage.setScene(menuScene));
        //create table and columns to display high scores
        Label playerColumn = new Label("Player");
        playerColumn.setMinWidth(100);
        playerColumn.setStyle("-fx-font-weight: bold");
        Label scoreColumn = new Label("Score");
        scoreColumn.setMinWidth(100);
        scoreColumn.setStyle("-fx-font-weight: bold");
        Label timeColumn = new Label("Time (s)");
        timeColumn.setMinWidth(100);
        timeColumn.setStyle("-fx-font-weight: bold");
        Label shipsKilledColumn = new Label("Ships Killed");
        shipsKilledColumn.setMinWidth(100);
        shipsKilledColumn.setStyle("-fx-font-weight: bold");

        //create and populate score vbox to contain score properties for the scoreboard
        VBox scoreOptions = new VBox();
        scoreOptions.getChildren().addAll(scoreMenu, new HBox(playerColumn, scoreColumn, timeColumn, shipsKilledColumn));
        scoreOptions.setMaxWidth(400);
        scoreOptions.setMaxHeight(200);
        scoreOptions.setBackground(Background.EMPTY);
        scoreOptions.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5);");

        //loop to populate the scoreboard with high scores
        int count = 0;      //counter for the loop
        Collections.sort(scores);
        //populate the score board with top 5 scores
        for(Score s : scores){
            Label user = new Label(s.getPlayerName());
            Label score = new Label(Long.toString(s.getScore()));
            Label time = new Label(Long.toString(s.getTime()));
            Label ships = new Label(Long.toString(s.getShipsKilled()));
            user.setMinWidth(100);
            score.setMinWidth(100);
            time.setMinWidth(100);
            ships.setMinWidth(100);

            if(count < 5) {
                scoreOptions.getChildren().add(new HBox(user, score, time, ships));
                count++;
            }
        }
        //add menu button
        scoreOptions.getChildren().add(toMenu);

        //create and populate stackpane
        StackPane scoreBox = new StackPane();
        StackPane.setAlignment(scoreBox, Pos.CENTER);
        scoreBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");//transparency so we can see the background
        scoreBox.getChildren().addAll(imageView2, scoreOptions);

        //set scene
        scoreScene = new Scene(scoreBox, 540, 960);
        primaryStage.setScene(menuScene);

        //these are the event handlers for obtaining input from the user during the game
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

        final long startTime = System.nanoTime();// this is the time that the program originally starts at from the system which we can use to calculate elapsed time
        //animation timer gets called every frame that the program is running
        new AnimationTimer()
        {
            //handle is called by AnimationTimer and is passed the time in nanoseconds from the system every frame
            public void handle(long currentTime) {
                long elapsedTime = currentTime - startTime;//we use this to calculate the elapsed time
                int elapsedFrames = Math.toIntExact(elapsedTime / 16_670_000); //convert elapsed time into 60 frames per second to pass to the game logic
                long lastUpdate = 0;
                if (currentTime - lastUpdate >= 16_670_000 && gameStart) {//only send updates to the game logic ever 60th of a second if gamestart is true
                    try {
                        game.logic(gc, elapsedFrames, input);
                        if (!game.running()){ //if the game is not running (that function gets set to false when the player dies)
                            gameStart = false;
                            try {// add a new score object to the database
                                DB.add(new Score(playerName, game.getScore(), game.getTime(), game.getShipsKilled()));
                            }catch (SQLException e){}
                            primaryStage.setScene(scoreScene);//go to the score screen
                        }
                    } catch (IOException | ConcurrentModificationException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        primaryStage.show();
    }
    //main method
    public static void main(String[] args) throws SQLException {
        launch(args);
        DB.close();
    }
}