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
    private static ArrayList<Laser> lasers = new ArrayList<Laser>();
    private static Sprite playerShip;
    private static Background space;
    private static Random rand = new Random();
    private static int randomPos;
    private static Timer delay = new Timer();

    public static void startLevel(int levelNumber) throws IOException {
        playerShip = new Player(240, 850, 72, 64, "Resources\\PlayerShip.png");
        Enemy enemyShip = new Enemy(240, 0, 72, 64, "Resources\\EnemyShip.png");
        enemyList.add(enemyShip);
        space = new Background("Resources\\BlueSpace.png", 5056);
    }


    static void logic(GraphicsContext gc, long frames, ArrayList<String> input) throws IOException, InterruptedException {
        if (input.contains("LEFT") && playerShip.getX() > 0)
            playerShip.move(-3);
        if (input.contains("RIGHT") && playerShip.getX() < 468)
            playerShip.move(3);
        if (frames % 30 == 0) {
            Laser laser = new Laser(playerShip.getX() + 33, playerShip.getY() - 12, 4, 6, "Resources\\bullet.png");
            lasers.add(laser);
        }

        if (frames % 3000 == 0) {
            Enemy enemyShip = new Enemy(rand.nextInt(540), 0, 72, 64, "Resources\\EnemyShip.png");
            enemyList.add(enemyShip);
        }


        space.scroll();
        space.render(gc);
        for (Enemy enemyShip : enemyList ) {
            enemyShip.move(1);
            enemyShip.render(gc);
            if (enemyShip.intersects(playerShip) || enemyShip.getY() > (playerShip.getY() + playerShip.getHeight())) {
                enemyShip.kill();
                playerShip.kill();
            }
        }
        for (Laser laser : lasers){
            if(laser.getY() >= 0) {
                laser.move(5);
                laser.render(gc);
                for (Enemy enemyShip : enemyList ){
                    if(enemyShip.intersects(laser)){
                        enemyShip.kill();
                        laser.kill();
                    }
                }
            }
            else{
                laser.kill();
            }
        }
        playerShip.render(gc);

    }


    void levelEnd(){
        enemyList.clear();
        lasers.clear();
    }
}