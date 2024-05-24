import java.awt.Rectangle;
import java.util.Random;

public class Obstacle {import java.awt.Rectangle;
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

            obstacle = new Rectangle(x, 600 - obstacleHeight, width, obstacleHeight);
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
