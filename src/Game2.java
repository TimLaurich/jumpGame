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
    private static final int spaceObstacles = 150;

    private Player player;
    private ArrayList<Obstacle> obstacles;
    private boolean gameOver;
    private Timer timer;
    private Background background;
    private Effects effects;
    private ScoreManager scoreManager;

    public Game2() {
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        addKeyListener(this);

        player = new Player();
        obstacles = new ArrayList<>();
        gameOver = false;
        background = new Background();
        effects = new Effects();
        scoreManager = new ScoreManager();

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
        scoreManager.reset();
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
            scoreManager.increment();
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
        background.draw(g);

        g.setColor(Color.RED);
        g.fillRect(player.getX(), player.getY(), 50, 50);

        g.setColor(Color.GREEN);
        for (Obstacle obstacle : obstacles) {
            g.fillRect(obstacle.getBounds().x, obstacle.getBounds().y, obstacle.getBounds().width, obstacle.getBounds().height);
        }

        effects.drawScore(g, scoreManager.getScore());

        if (gameOver) {
            effects.drawGameOver(g, width, height);
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
