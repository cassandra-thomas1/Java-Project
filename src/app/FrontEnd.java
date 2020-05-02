package app;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.image.Image;

public class FrontEnd extends Application{
    @Override
    public void start(Stage primaryStage) throws IOException {
        Group root = new Group();
        Canvas canvas = new Canvas (1280, 720);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //loading assets
        Image test = new Image(new URL("https://wizardlyshoe4.s-ul.eu/3QeXMcHs").openStream());
        gc.drawImage(test, 0, 0);

        //Create and display scene
        Scene scene = new Scene(root);
        primaryStage.setTitle("Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}