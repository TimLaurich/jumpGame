import java.awt.Graphics;
import java.awt.Color;

public class Background {
    private static final int width = 800;
    private static final int height = 600;

    public void draw(Graphics g) {
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.GREEN);
        g.fillRect(0, height - 100, width, 100);
    }
}
