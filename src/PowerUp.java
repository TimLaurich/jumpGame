import java.awt.*;

public class PowerUp {
    private static final int width = 20;
    private static final int height = 20;
    private static final int speed = 5;

    private int x, y;
    private Rectangle powerUp;

    public PowerUp(int startX) {
        x = startX;
        y = 600 - 100 - height;
        powerUp = new Rectangle(x, y, width, height);
    }

    public void update() {
        x -= speed;
        powerUp.setLocation(x, y);
    }

    public Rectangle getBounds() {
        return powerUp;
    }

    public boolean isOffScreen() {
        return x + width < 0;
    }
}