package app;

/*
REFORMAT AFTER SETTING UP ABSTRACT CLASSES

 */
import javafx.scene.canvas.GraphicsContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;


//this needs to be commented too
public class game {

    private static ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
    private static Sprite playerShip;
    private static Sprite backGround;
    private static Random rand = new Random();
    private static int randomPos;

    public static void startLevel(int levelNumber) throws IOException {
        playerShip = new Player(240, 850, 72, 64, "Resources\\PlayerShip.png");
        Enemy enemyShip = new Enemy(240, 0, 72, 64, "Resources\\EnemyShip.png");
        enemyList.add(enemyShip);
    }


    static void logic(GraphicsContext gc, long elapsedTime, ArrayList<String> input) throws IOException {

        //if (backGround.getY() == 0)
        //    backGround.setPosition(0, -4096);
        //backGround.setPosition(0, backGround.getY() + 1);
        if (input.contains("LEFT") && playerShip.getX() > 0)
            playerShip.move(-3);
        if (input.contains("RIGHT") && playerShip.getX() < 468)
            playerShip.move(3);
        //backGround.render(gc);
        for (Enemy enemyShip : enemyList ) {
            enemyShip.move(3);
            enemyShip.render(gc);
            if (enemyShip.intersects(playerShip)) {
                enemyShip.kill();
                playerShip.kill();
            }
        }
        if (playerShip.isAlive())
            playerShip.render(gc);
    }
}
