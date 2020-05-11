package app;

/*
REFORMAT AFTER SETTING UP ABSTRACT CLASSES

 */
import javafx.scene.canvas.GraphicsContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


//this needs to be commented too
public class game {

    private static ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
    private static ArrayList<Laser> lasers = new ArrayList<Laser>();
    private static String[] backgrounds = new String[] {"Resources\\BlueSpace.png", "Resources\\ResSpace.png", "Resources\\PinkSpace.png"};
    private static Sprite playerShip;
    private static Background space;
    private static Random rand = new Random();
    private static int randomPos;
    private static long score = 0;
    private static ScoreCounter dispScore;
    private static long time = 0;
    private static Timer dispTimer;

    public static void startLevel() throws IOException {
        playerShip = new Player(240, 850, 72, 64, "Resources\\PlayerShip.png");
        Enemy enemyShip = new Enemy(240, 0, 72, 64, "Resources\\EnemyShip.png");
        dispScore = new ScoreCounter(50, 20, score);
        dispTimer = new Timer(50, 40, time);
        enemyList.add(enemyShip);
        space = new Background(backgrounds[rand.nextInt(3)], 5056);
    }


    static void logic(GraphicsContext gc, long frames, ArrayList<String> input) throws IOException, InterruptedException {
        if (input.contains("LEFT") && playerShip.getX() > 0)
            playerShip.move(-3);
        if (input.contains("RIGHT") && playerShip.getX() < 468)
            playerShip.move(3);
        if (frames % 60 == 0) {
            Laser laser = new Laser(playerShip.getX() + 33, playerShip.getY() - 12, 4, 6, "Resources\\bullet.png");
            lasers.add(laser);

            //update Timer
            time += 1;
            dispTimer.updateText(time);
        }



        if (frames % 90 == 0) {
            Enemy enemyShip = new Enemy(rand.nextInt(540 - 72), 0 - 64, 72, 64, "Resources\\EnemyShip.png");
            enemyList.add(enemyShip);
        }

        if(frames % 120 == 0){
            score += 10;
            dispScore.updateText(score);
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
                        enemyList.remove(enemyShip);
                        score += 50;
                        dispScore.updateText(score);
                    }
                }
            }
            else{
                laser.kill();
            }
        }
        playerShip.render(gc);
        dispScore.render(gc);
        dispTimer.render(gc);

    }

    static boolean running(){
        if(!playerShip.isAlive()){
            levelEnd();
        }
        return playerShip.isAlive();
    }

    static long getScore(){
        return score;
    }

    static void levelEnd(){
        enemyList.clear();
        lasers.clear();
    }
}