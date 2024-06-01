

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
    private PowerUp powerUp;
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
        powerUp = null;
        gameOver = false;
        paused = false;
        background = new Background();
        effects = new Effects();
        scoreManager = new ScoreManager();
        loadHighScore();

        timer = new Timer(20, this);
        timer.start();

        generateInitialObstacles();
        generateInitialPowerUp();
    }


    /**
     * Generates the initial obstacles for the game.
     */
    public void generateInitialObstacles() {
        for (int i = 0; i < 3; i++) {
            generateObstacle(width + i * spaceObstacles);
        }
    }

    public int getHighScore() {
        return highScore;
    }


    /**
     * Generates the initial power-up for the game.
     */
    public void generateInitialPowerUp() {
        generatePowerUp(width + spaceObstacles);
    }

    /**
     * Generates a new obstacle at the specified x-coordinate.
     *
     * @param startX the x-coordinate of the new obstacle
     */
    public void generateObstacle(int startX) {
        obstacles.add(new Obstacle(startX));
    }

    /**
     * Generates a new power-up at the specified x-coordinate.
     *
     * @param startX the x-coordinate of the new power-up
     */
    public void generatePowerUp(int startX) {
        powerUp = new PowerUp(startX);
    }

    /**
     * Resets the game to its initial state.
     */
    public void resetGame() {
        player.reset();
        obstacles.clear();
        powerUp = null;
        generateInitialObstacles();
        generateInitialPowerUp();
        scoreManager.reset();
        gameOver = false;
        paused = false;
        timer.start();
    }

    /**
     * Updates the game state and repaints the panel.
     *
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver && !paused) {
            player.update();
            for (Obstacle obstacle : obstacles) {
                obstacle.update();
            }
            if (powerUp != null) {
                powerUp.update();
            }
            if (obstacles.get(0).isOffScreen()) {
                obstacles.remove(0);
                generateObstacle(width + spaceObstacles);
            }
            if (powerUp != null && powerUp.isOffScreen()) {
                powerUp = null;
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
    public void checkCollision() {
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
        if (powerUp != null && powerUp.getBounds().intersects(playerBounds)) {
            player.increaseJumpPower();
            powerUp = null;
        }
    }

    /**
     * Updates the high score if the current score is higher.
     */
    public void updateHighScore() {
        if (scoreManager.getScore() > highScore) {
            highScore = scoreManager.getScore();
            saveHighScore();
        }
    }

    /**
     * Saves the high score to a file.
     */
    public void saveHighScore() {
        try (PrintWriter writer = new PrintWriter("highscore.txt")) {
            writer.println(highScore);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the high score from a file.
     */
    public void loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader("highscore.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                highScore = Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the game components on the panel.
     *
     * @param g the graphics object
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

        if (powerUp != null) {
            g.setColor(Color.BLUE);
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

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Handles key press events.
     *
     * @param e the key event
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE && !gameOver && !paused) {
            player.jump();
        } else if (e.getKeyCode() == KeyEvent.VK_P) {
            paused = !paused;
        } else if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            resetGame();
        } else if (e.getKeyCode() == KeyEvent.VK_M && gameOver) {
            returnToMenu();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    /**
     * Returns to the main menu.
     */
    public void returnToMenu() {
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (topFrame != null) {
            topFrame.getContentPane().removeAll();
            GameMenu menu = new GameMenu(topFrame);
            topFrame.add(menu);
            topFrame.revalidate();
            topFrame.repaint();
        }
    }


}