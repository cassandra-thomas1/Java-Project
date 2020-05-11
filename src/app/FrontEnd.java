package app;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
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
        ObservableList<Score> oListScores = FXCollections.observableArrayList(scores);
        primaryStage.setTitle("Space Shooter");
        //main menu button actions
        TextField nameEntry = new TextField();
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
            Collections.sort(scores);
            //push score array onto table

            primaryStage.setScene(scoreScene);
        };
        //menu Background
        ImageView imageView = new ImageView(new Image(new FileInputStream("Resources\\background.png")));
        ImageView imageView2 = new ImageView(new Image(new FileInputStream("Resources\\background.png")));

        Label namePls = new Label("Name: ");
        namePls.setStyle("-fx-text-fill: #00ffbd;");
        Button toGame = new Button("Start game");
        Button toScore = new Button("High scores");
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
        //labels, buttons, and event handlers
        Label scoreMenu = new Label("High Scores");
        scoreMenu.setStyle("-fx-text-fill: #00ffbd;");
        Button toMenu = new Button("Main Menu");
        toMenu.setOnAction(e->primaryStage.setScene(menuScene));
        //create table
        table.setEditable(true);
        TableColumn playerColumn = new TableColumn("Player");
        playerColumn.setMinWidth(100);
        playerColumn.setCellValueFactory(
                new PropertyValueFactory<Player, String>("playerName"));
        TableColumn scoreColumn = new TableColumn("Score");
        scoreColumn.setMinWidth(100);
        scoreColumn.setCellValueFactory(
                new PropertyValueFactory<Player, String>("score"));
        TableColumn timeColumn = new TableColumn("Time");
        timeColumn.setMinWidth(100);
        timeColumn.setCellValueFactory(
                new PropertyValueFactory<Player, String>("time"));
        TableColumn shipsKilledColumn = new TableColumn("Ships Killed");
        shipsKilledColumn.setMinWidth(100);
        shipsKilledColumn.setCellValueFactory(
                new PropertyValueFactory<Player, String>("shipsKilled"));
        table.setItems(oListScores);
        table.getColumns().addAll(playerColumn, scoreColumn, timeColumn, shipsKilledColumn);
        //create and populate vbox
        VBox scoreOptions = new VBox();
        scoreOptions.getChildren().addAll(scoreMenu, table, toMenu);
        scoreOptions.setMaxWidth(400);
        scoreOptions.setMaxHeight(200);
        //create and populate stackpane
        StackPane scoreBox = new StackPane();
        StackPane.setAlignment(scoreBox, Pos.CENTER);
        scoreBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
        scoreBox.getChildren().addAll(imageView2, scoreOptions);
        //set scene
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
                                try {
                                    DB.add(new Score(playerName, game.getScore(), game.getTime(), game.getShipsKilled()));
                                }catch (SQLException e){
                                    System.out.println("Error Adding to DB");}

                                primaryStage.setScene(scoreScene);
                            }
                        } catch (IOException | ConcurrentModificationException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }.start();
        primaryStage.show();
    }
    public static void main(String[] args) throws SQLException {
        launch(args);
        DB.close();
    }
}