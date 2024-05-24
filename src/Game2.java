import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Game2 extends JPanel implements KeyListener, ActionListener {
    private static final int width = 800;
    private static final int height = 600;
    private static final int spaceObstacles = 150;  // Menší vzdálenost mezi překážkami

    private Player player;
    private ArrayList<Obstacle> obstacles;
    private boolean gameOver;
    private int score;
    private Timer timer;

    public Game2() {
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        addKeyListener(this);

        player = new Player();
        obstacles = new ArrayList<>();
        score = 0;
        gameOver = false;

        timer = new Timer(15, this);  // Rychlejší časovač
        timer.start();

        generateInitialObstacles();
    }

    private void generateInitialObstacles() {
        for (int i = 0; i < 5; i++) {  // Více počátečních překážek
            generateObstacle(width + i * spaceObstacles);
        }
    }

    private void generateObstacle(int startX) {
        obstacles.add(new Obstacle(startX));
    }

    private void resetGame() {
        player.reset();
        obstacles.clear();
        generateInitialObstacles();
        score = 0;
        gameOver = false;
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            player.update();
            for (Obstacle obstacle : obstacles) {
                obstacle.update();
            }
            if (obstacles.get(0).isOffScreen()) {
                obstacles.remove(0);
                generateObstacle(width + spaceObstacles);
            }
            checkCollision();
            score++;
            repaint();
        }
    }

    private void checkCollision() {
        Rectangle playerBounds = player.getBounds();
        for (Obstacle obstacle : obstacles) {
            if (obstacle.getBounds().intersects(playerBounds)) {
                gameOver = true;
                timer.stop();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.RED);
        g.fillRect(player.getX(), player.getY(), 50, 50);

        g.setColor(Color.GREEN);
        for (Obstacle obstacle : obstacles) {
            g.fillRect(obstacle.getBounds().x, obstacle.getBounds().y, obstacle.getBounds().width, obstacle.getBounds().height);
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + score, 10, 30);

        if (gameOver) {
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Game Over!", width / 2 - 100, height / 2);
            g.drawString("Press 'R' to Restart", width / 2 - 150, height / 2 + 50);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver) {
            player.jump();
        }
        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            resetGame();
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {}



}
