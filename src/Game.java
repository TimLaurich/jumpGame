import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JPanel implements KeyListener, ActionListener {
    private static final int width = 800;
    private static final int height = 600;
    private static final int spaceObstacles = 200;

    private Player player;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<PowerUp> powerUps;
    private boolean gameOver;
    private Timer timer;
    private Background background;
    private Effects effects;
    private ScoreManager scoreManager;

    public Game() {
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        addKeyListener(this);

        player = new Player();
        obstacles = new ArrayList<>();
        powerUps = new ArrayList<>();
        gameOver = false;
        background = new Background();
        effects = new Effects();
        scoreManager = new ScoreManager();

        timer = new Timer(20, this);
        timer.start();

        generateInitialObstacles();
        generateInitialPowerUps();
    }

    private void generateInitialObstacles() {
        for (int i = 0; i < 3; i++) {
            generateObstacle(width + i * spaceObstacles);
        }
    }

    private void generateInitialPowerUps() {
        for (int i = 0; i < 2; i++) {
            generatePowerUp(width + i * spaceObstacles);
        }
    }

    private void generateObstacle(int startX) {
        obstacles.add(new Obstacle(startX));
    }

    private void generatePowerUp(int startX) {
        powerUps.add(new PowerUp(startX));
    }

    private void resetGame() {
        player.reset();
        obstacles.clear();
        powerUps.clear();
        generateInitialObstacles();
        generateInitialPowerUps();
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
            for (PowerUp powerUp : powerUps) {
                powerUp.update();
            }
            if (obstacles.get(0).isOffScreen()) {
                obstacles.remove(0);
                generateObstacle(width + spaceObstacles);
            }
            if (powerUps.get(0).isOffScreen()) {
                powerUps.remove(0);
                generatePowerUp(width + spaceObstacles);
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
        for (PowerUp powerUp : powerUps) {
            if (powerUp.getBounds().intersects(playerBounds)) {
                player.increaseJumpPower();
                powerUps.remove(powerUp);
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

        g.setColor(Color.BLUE);
        for (PowerUp powerUp : powerUps) {
            g.fillRect(powerUp.getBounds().x, powerUp.getBounds().y, powerUp.getBounds().width, powerUp.getBounds().height);
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
        if (e.getKeyCode() == KeyEvent.VK_SPACE &&!gameOver) {
            player.jump();
        }
        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            resetGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}