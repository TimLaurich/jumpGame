import java.awt.*;

class Player {
    private static final int characterWidth = 50;
    private static final int characterHeight = 50;
    private static final int startX = 100;
    private static final int startY = 600 - 100 - characterHeight;
    private static final int gravity = 1;
    private static final int jumpPower = -15;

    private int x, y, velocityY;
    private boolean jumping;
    private int jumpPowerMultiplier;

    public Player() {
        x = startX;
        y = startY;
        velocityY = 0;
        jumping = false;
        jumpPowerMultiplier = 1;
    }

    public void update() {
        if (jumping) {
            velocityY = jumpPower * jumpPowerMultiplier;
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
        jumpPowerMultiplier = 1;
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

    public void increaseJumpPower() {
        jumpPowerMultiplier += 1;
    }
}