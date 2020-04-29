package app;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class FrontEnd extends Application{
    @Override
    public void start(Stage stage) throws FileNotFoundException{
        Image test = new Image(new FileInputStream("C:\\Users\\Joey\\IdeaProjects\\Java-Project\\kenyualtother.png"));
    }
}