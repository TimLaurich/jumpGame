import java.awt.Rectangle;
import java.util.Random;

public class Obstacle {
    private static final int width = 50;
    private static final int maxHeight = 50;  // Maximální výška překážky menší než skoková síla
    private static final int speed = 5;

    private int x, y;
    private Rectangle obstacle;

    public Obstacle(int startX) {
        x = startX;
        Random random = new Random();
        int obstacleHeight = 20 + random.nextInt(maxHeight);  // Nastavení maximální výšky překážky

        obstacle = new Rectangle(x, 600 - 100 - obstacleHeight, width, obstacleHeight);
    }

    public void update() {
        x -= speed;
        obstacle.setLocation(x, obstacle.y);
    }

    public Rectangle getBounds() {
        return obstacle;
    }

    public int getX() {
        return x;
    }

    public boolean isOffScreen() {
        return x + width < 0;
    }
}
