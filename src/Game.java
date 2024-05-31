import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Game extends JPanel implements KeyListener, ActionListener {
    private static final int width = 800;
    private static final int height = 600;
    private static final int spaceObstacles = 200;

    private Player player;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<PowerUp> powerUps;
    private boolean gameOver;
    private boolean paused;
    private Timer timer;
    private Background background;
    private Effects effects;
    private ScoreManager scoreManager;
    private int highScore;


    public Game() {
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        addKeyListener(this);

        player = new Player();
        obstacles = new ArrayList<>();
        powerUps = new ArrayList<>();
        gameOver = false;
        paused = false;
        background = new Background();
        effects = new Effects();
        scoreManager = new ScoreManager();
        loadHighScore();

        timer = new Timer(20, this);
        timer.start();

        generateInitialObstacles();
        generateInitialPowerUps();
    }

    /**
     * Generates the initial obstacles for the game.
     */
    private void generateInitialObstacles() {
        for (int i = 0; i < 3; i++) {
            generateObstacle(width + i * spaceObstacles);
        }
    }

    /**
     * Generates the initial power-ups for the game.
     */
    private void generateInitialPowerUps() {
        for (int i = 0; i < 2; i++) {
            generatePowerUp(width + i * spaceObstacles);
        }
    }

    /**
     * Generates a new obstacle at the specified x-coordinate.
     *
     * @param startX the x-coordinate where the obstacle should be generated
     */
    private void generateObstacle(int startX) {
        obstacles.add(new Obstacle(startX));
    }

    /**
     * Generates a new power-up at the specified x-coordinate.
     *
     * @param startX the x-coordinate where the power-up should be generated
     */
    private void generatePowerUp(int startX) {
        powerUps.add(new PowerUp(startX));
    }

    /**
     * Resets the game to its initial state.
     */
    private void resetGame() {
        player.reset();
        obstacles.clear();
        powerUps.clear();
        generateInitialObstacles();
        generateInitialPowerUps();
        scoreManager.reset();
        gameOver = false;
        paused = false;
        timer.start();
    }

    /**
     * Updates the game state and checks for collisions.
     *
     * @param e the action event that triggered this method.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver &&!paused) {
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
            updateHighScore();
            repaint();
        }
    }

    /**
     * Checks for collisions between the player and obstacles or power-ups.
     */
    private void checkCollision() {
        Rectangle playerBounds = player.getBounds();
        Iterator<Obstacle> obstacleIterator = obstacles.iterator();
        while (obstacleIterator.hasNext()) {
            Obstacle obstacle = obstacleIterator.next();
            if (obstacle.getBounds().intersects(playerBounds)) {
                gameOver = true;
                timer.stop();
                obstacleIterator.remove();
            }
        }
        Iterator<PowerUp> powerUpIterator = powerUps.iterator();
        while (powerUpIterator.hasNext()) {
            PowerUp powerUp = powerUpIterator.next();
            if (powerUp.getBounds().intersects(playerBounds)) {
                player.increaseJumpPower();
                powerUpIterator.remove();
            }
        }
    }

    /**
     * Updates the high score if the current score is higher.
     */
    private void updateHighScore() {
        if (scoreManager.getScore() > highScore) {
            highScore = scoreManager.getScore();
            saveHighScore();
        }
    }

    /**
     * Saves the high score to a file.
     */
    private void saveHighScore() {
        try (PrintWriter writer = new PrintWriter("highscore.txt")) {
            writer.println(highScore);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the high score from a file.
     */
    private void loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader("highscore.txt"))) {
            String line = reader.readLine();
            if (line!= null) {
                highScore = Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Paints the game components onto the screen.
     *
     * @param g the graphics object to paint with
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        background.draw(g);

        g.drawImage(player.getImage(), player.getX(), player.getY(), player.getCharacterWidth(), player.getCharacterHeight(), null);

        g.setColor(Color.GREEN);
        for (Obstacle obstacle : obstacles) {
            g.fillRect(obstacle.getBounds().x, obstacle.getBounds().y, obstacle.getBounds().width, obstacle.getBounds().height);
        }

        g.setColor(Color.BLUE);
        for (PowerUp powerUp : powerUps) {
            g.fillRect(powerUp.getBounds().x, powerUp.getBounds().y, powerUp.getBounds().width, powerUp.getBounds().height);
        }

        effects.drawScore(g, scoreManager.getScore());
        effects.drawHighScore(g, highScore);

        if (gameOver) {
            effects.drawGameOver(g, width, height);
        } else if (paused) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Paused", width / 2 - 100, height / 2);
        }
    }

    /**
     * Handles key presses for game controls.
     *
     * @param e the key event that triggered this method
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE &&!gameOver &&!paused) {
            player.jump();
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            paused =!paused;
        } else if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            resetGame();
        } else if (e.getKeyCode() == KeyEvent.VK_M && gameOver) {
            returnToMenu();
        }
    }

    /**
     * Returns to the main menu when the 'M' key is pressed.
     */
    private void returnToMenu() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame!= null) {
            topFrame.getContentPane().removeAll();
            GameMenu menu = new GameMenu(topFrame);
            topFrame.add(menu);
            topFrame.revalidate();
            topFrame.repaint();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}