import java.awt.*;

public class PowerUp {
    private static final int POWER_UP_WIDTH = 20;
    private static final int POWER_UP_HEIGHT = 20;
    private static final int POWER_UP_SPEED = 5;

    private int xCoordinate;
    private int yCoordinate;
    private Rectangle powerUpBounds;

    /**
     * Initializes the power-up at the specified starting x-coordinate.
     *
     * @param startX the starting x-coordinate of the power-up
     */
    public PowerUp(int startX) {
        xCoordinate = startX - 50;
        yCoordinate = 600 - 100 - POWER_UP_HEIGHT;
        powerUpBounds = new Rectangle(xCoordinate, yCoordinate, POWER_UP_WIDTH, POWER_UP_HEIGHT);
    }

    /**
     * Updates the position of the power-up.
     */
    public void update() {
        xCoordinate -= POWER_UP_SPEED;
        powerUpBounds.setLocation(xCoordinate, yCoordinate);
    }

    /**
     * Returns the bounds of the power-up.
     *
     * @return the bounds of the power-up
     */
    public Rectangle getBounds() {
        return powerUpBounds;
    }

    /**
     * Checks if the power-up is off-screen.
     *
     * @return true if the power-up is off-screen, false otherwise
     */
    public boolean isOffScreen() {
        return xCoordinate + POWER_UP_WIDTH < 0;
    }

}