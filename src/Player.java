import java.awt.Rectangle;

public class Player {
    private static final int characterWidth = 50;
    private static final int characterHeight = 50;
    private static final int startX = 100;
    private static final int startY = 600 - 100 - characterHeight;
    private static final int gravity = 1;
    private static final int jumpPower = -15;

    private int x, y, velocityY;
    private boolean jumping;

    public Player() {
        x = startX;
        y = startY;
        velocityY = 0;
        jumping = false;
    }

    public void update() {
        if (jumping) {
            velocityY = jumpPower;
            jumping = false;
        } else {
            velocityY += gravity;
        }
        y += velocityY;

        if (y > startY) {
            y = startY;
            velocityY = 0;
        }
    }

    public void jump() {
        if (y == startY) {
            jumping = true;
        }
    }

    public void reset() {
        x = startX;
        y = startY;
        velocityY = 0;
        jumping = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, characterWidth, characterHeight);
    }
}
