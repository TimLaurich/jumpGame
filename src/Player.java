import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class Player {
    private static final int characterWidth = 50;
    private static final int characterHeight = 50;
    private static final int startX = 100;
    private static final int startY = 600 - 100 - characterHeight;
    private static final int gravity = 1;
    private static final int jumpPower = -15;

    private int x, y, velocityY;
    private boolean jumping;
    private int jumpPowerMultiplier;
    private BufferedImage playerImage;


    public Player() {
        x = startX;
        y = startY;
        velocityY = 0;
        jumping = false;
        jumpPowerMultiplier = 1;

        try {
            playerImage = ImageIO.read(new File("player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Updates the player's position.
     */
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

    /**
     * Makes the player jump.
     */
    public void jump() {
        if (y == startY) {
            jumping = true;
        }
    }

    /**
     * Resets the player's position and attributes.
     */
    public void reset() {
        x = startX;
        y = startY;
        velocityY = 0;
        jumping = false;
        jumpPowerMultiplier = 1;
    }

    /**
     * Returns the player's x-coordinate.
     *
     * @return the player's x-coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the player's y-coordinate.
     *
     * @return the player's y-coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the bounds of the player.
     *
     * @return the bounds of the player
     */
    public Rectangle getBounds() {
        return new Rectangle(x, y, characterWidth, characterHeight);
    }

    /**
     * Increases the player's jump power.
     */
    public void increaseJumpPower() {
        jumpPowerMultiplier += 1;
    }

    /**
     * Returns the player's image.
     *
     * @return the player's image
     */
    public BufferedImage getImage() {
        return playerImage;
    }

    /**
     * Returns the width of the player character.
     *
     * @return the width of the player character
     */
    public int getCharacterWidth() {
        return characterWidth;
    }

    /**
     * Returns the height of the player character.
     *
     * @return the height of the player character
     */
    public int getCharacterHeight() {
        return characterHeight;
    }
}