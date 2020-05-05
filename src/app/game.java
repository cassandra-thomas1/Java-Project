package app;

import javafx.scene.canvas.GraphicsContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
//this needs to be commented too
public class game {
    private static ArrayList<Sprite> enemyList = new ArrayList<Sprite>();
    private static Sprite playerShip;
    private static Sprite backGround;
    private static Random rand = new Random();
    private static int randomPos;
    public static void startLevel(int levelNumber) throws IOException {
        playerShip = new Sprite(240, 850, 72, 64, "https://wizardlyshoe4.s-ul.eu/JavaProject/PcY9pXSJ");
        backGround = new Sprite(0, -4096, "https://wizardlyshoe4.s-ul.eu/JavaProject/wMSF3G0R");
        for (int i = 0; i < levelNumber; ++i) {
            randomPos = rand.nextInt(240);
            Sprite enemyShip = new Sprite(randomPos, 0, 72, 64, "https://wizardlyshoe4.s-ul.eu/JavaProject/6ayA0vm0");
            enemyList.add(enemyShip);
        }
    }
    static void logic(GraphicsContext gc, long elapsedTime, ArrayList<String> input) throws IOException {
        if (backGround.getY() == 0)
            backGround.setPosition(0, -4096);
        backGround.setPosition(0, backGround.getY() + 1);
        if (input.contains("LEFT") && playerShip.getX() > 0)
            playerShip.move(-3, 0);
        if (input.contains("RIGHT") && playerShip.getX() < 468)
            playerShip.move(3, 0);
        backGround.render(gc);
        for (Sprite enemyShip : enemyList ) {
            enemyShip.setPosition(enemyShip.getX() + Math.cos(elapsedTime) * 2, enemyShip.getY() + 3);
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
