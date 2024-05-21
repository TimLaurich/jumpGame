import java.awt.Rectangle;

public class Player {
    private static final int width = 50;
    private static final int height = 50;
    private static final int startX = 100;
    private static final int startY = 600 - height;
    private static final int gravity = 1;
    private static final int jumpPower = -20;  // Zvýšená skoková síla

    private int x, y, velocityY;
    private boolean jumping;

    public Player() {
        x = startX;
        y = startY;
        velocityY = 0;
        jumping = false;
    }

    public void jump() {
        if (!jumping) {
            velocityY = jumpPower;
            jumping = true;
        }
    }

    public void update() {
        velocityY += gravity;
        y += velocityY;

        if (y > startY) {
            y = startY;
            velocityY = 0;
            jumping = false;
        }
    }

    public void reset() {
        x = startX;
        y = startY;
        velocityY = 0;
        jumping = false;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
