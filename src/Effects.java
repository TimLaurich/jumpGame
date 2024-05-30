import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Effects {
    public void drawGameOver(Graphics g, int width, int height) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Game Over!", width / 2 - 100, height / 2);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Press 'R' to Restart", width / 2 - 100, height / 2 + 50);
        g.drawString("Press 'M' for Main Menu", width / 2 - 100, height / 2 + 80);
    }



    public void drawScore(Graphics g, int score) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 30);
    }
}