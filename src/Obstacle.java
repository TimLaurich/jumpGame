import java.awt.Rectangle;
import java.util.Random;


public class Obstacle {
    private static final int width = 20;
    private static final int maxHeight = 50;
    private static final int speed = 5;

    private int x, y;
    private Rectangle obstacle;


    public Obstacle(int startX) {
        x = startX;
        Random random = new Random();
        int obstacleHeight = 20 + random.nextInt(maxHeight);

        obstacle = new Rectangle(x, 600 - 100 - obstacleHeight, width, obstacleHeight);
    }

    /**
     * Updates the obstacle's position.
     */
    public void update() {
        x -= speed;
        obstacle.setLocation(x, obstacle.y);
    }

    /**
     * Returns the bounds of the obstacle.
     *
     * @return the bounds of the obstacle
     */
    public Rectangle getBounds() {
        return obstacle;
    }

    /**
     * Returns the x-coordinate of the obstacle.
     *
     * @return the x-coordinate of the obstacle
     */
    public int getX() {
        return x;
    }

    /**
     * Checks if the obstacle is off the screen.
     *
     * @return true if the obstacle is off the screen, false otherwise
     */
    public boolean isOffScreen() {
        return x + width < 0;
    }
}