import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class Background {
    private static final int width = 800;
    private static final int height = 600;
    private BufferedImage backgroundImage;
    private Rectangle floor;


    public Background() {
        try {
            backgroundImage = ImageIO.read(new File("sky.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int floorHeight = 100;
        floor = new Rectangle(0, height - floorHeight, width, floorHeight);
    }

    /**
     * Draws the background, including the sky image and the floor.
     *
     * @param g the graphics object to draw on
     */
    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, width, height, null);

        g.setColor(Color.GREEN);
        g.fillRect(floor.x, floor.y, floor.width, floor.height);
    }
}