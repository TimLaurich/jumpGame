import java.awt.Rectangle;
import java.util.Random;

public class Obstacle {
    private static final int width = 50;
    private static final int height = 600;
    private static final int space = 200;
    private static final int speed = 5;

    private int x;
    private Rectangle topObstacle, bottomObstacle;

    public Obstacle(int startX) {
        x = startX;
        Random random = new Random();
        int gapPosition = 50 + random.nextInt(height / 2);

        topObstacle = new Rectangle(x, 0, width, gapPosition);
        bottomObstacle = new Rectangle(x, gapPosition + space, width, height - gapPosition - space);
    }

    public void update() {
        x -= speed;
        topObstacle.setLocation(x, 0);
        bottomObstacle.setLocation(x, topObstacle.height + space);
    }

    public Rectangle getTopBounds() {
        return topObstacle;
    }

    public Rectangle getBottomBounds() {
        return bottomObstacle;
    }

    public int getX() {
        return x;
    }

    public boolean isOffScreen() {
        return x + width < 0;
    }
}
