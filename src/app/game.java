package app;

/*
REFORMAT AFTER SETTING UP ABSTRACT CLASSES

 */
import javafx.scene.canvas.GraphicsContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Random;


//this needs to be commented too
public class game {

    private static ArrayList<Enemy> enemyList = new ArrayList<Enemy>();     //containers that hold the lasers and enemy ships
    private static ArrayList<Laser> lasers = new ArrayList<Laser>();
    private static String[] backgrounds = new String[] {"Resources\\BlueSpace.png", "Resources\\ResSpace.png", "Resources\\PinkSpace.png"};     //container that holds the scrolling background
    private static Sprite playerShip;
    private static Background space;
    private static Random rand = new Random();
    private static long score = 0;
    private static ScoreCounter dispScore;
    private static long time = 0;
    private static Timer dispTimer;
    private static int shipsKilled = 0;

    //pre: game is started
    //post: game is initialized with all the properties of the game
    public static void startLevel() throws IOException {
        playerShip = new Player(240, 850, 72, 64, "Resources\\PlayerShip.png");
        Enemy enemyShip = new Enemy(240, 0, 72, 64, "Resources\\EnemyShip.png");
        dispScore = new ScoreCounter(50, 20, score);
        dispTimer = new Timer(50, 40, time);
        enemyList.add(enemyShip);
        space = new Background(backgrounds[rand.nextInt(3)], 5056);
    }

    //pre: the game is running at 60 frames/second
    //post: handles game logic such as movement, collisions and score accumulation
    static void logic(GraphicsContext gc, long frames, ArrayList<String> input) throws IOException, ConcurrentModificationException {
        //the background continuously is moving and
        //being rendered to place an effect of moving through space
        space.scroll();
        space.render(gc);

        //player ship moves based on key movements
        if (input.contains("LEFT") && playerShip.getX() > 0)
            playerShip.move(-3);
        if (input.contains("RIGHT") && playerShip.getX() < 468)
            playerShip.move(3);

        //every second, player shoots a laser, and the timer is manually updated
        if (frames % 60 == 0) {
            Laser laser = new Laser(playerShip.getX() + 33, playerShip.getY() - 12, 4, 6, "Resources\\bullet.png");
            lasers.add(laser);

            //update Timer
            time += 1;
            dispTimer.updateText(time);
        }


        //every 1.5 seconds, an enemy is spawned from the top of the screen
        if (frames % 90 == 0) {
            Enemy enemyShip = new Enemy(rand.nextInt(540 - 72), 0 - 64, 72, 64, "Resources\\EnemyShip.png");
            enemyList.add(enemyShip);
        }


        //every 2 seconds, the player gets points for being alive
        if(frames % 120 == 0){
            score += 10;
            dispScore.updateText(score);
        }


        //both loops below handle the movement and
        // collision status of the enemy ships and lasers
        for (Enemy enemyShip : enemyList ) {
            enemyShip.move(1);
            enemyShip.render(gc);
            if (enemyShip.intersects(playerShip) || enemyShip.getY() > (playerShip.getY() + playerShip.getHeight())) {
                enemyShip.kill();
                playerShip.kill();      //the game will end if the player dies
            }
        }

        for (Laser laser : lasers){
            //lasers only exist in the game bounds
            if(laser.getY() >= 0) {
                laser.move(5);
                laser.render(gc);
                for (Enemy enemyShip : enemyList ){
                    //when an enemy is hit, points are added to the score
                    //and the enemy and laser no longer exist
                    if(enemyShip.intersects(laser)){
                        enemyShip.kill();
                        laser.kill();
                        enemyList.remove(enemyShip);
                        score += 50;
                        dispScore.updateText(score);
                        shipsKilled++;
                    }
                }
            }
            else{
                laser.kill();       //the laser has passed y = 0
            }
        }

        //displays singular assets of the game
        playerShip.render(gc);
        dispScore.render(gc);
        dispTimer.render(gc);



    }

    //pre: frontend needs to check if the game is still in session
    //post: If the player is still alive, return it's status(true), else end the level and return it's status(false)
    static boolean running(){
        if(!playerShip.isAlive()){
            levelEnd();
        }
        return playerShip.isAlive();
    }

    //pre: the game properties are needed
    //post: returns the necessary value
    static long getScore(){
        return score;
    }
    static long getTime() {return time;}
    static int getShipsKilled(){ return shipsKilled;}

    //pre: the player ship has been killed
    //post: clears the containers holding the lasers and ships
    static void levelEnd(){
        enemyList.clear();
        lasers.clear();
    }
}