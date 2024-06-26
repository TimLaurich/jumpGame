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

public class Game2 extends JPanel implements KeyListener, ActionListener {
    private static final int width = 800;
    private static final int height = 600;
    private static final int spaceObstacles = 200;
    private static final int powerUpProbability = 20;

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

    public Game2() {
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

        timer = new Timer(15, this);
        timer.start();

        generateInitialObstacles();
        generateInitialPowerUp();
    }


    /**
     * Generates the initial obstacles for the game.
     */
    public void generateInitialObstacles() {
        for (int i = 0; i < 5; i++) {
            generateObstacle(width + i * spaceObstacles);
        }
    }

    /**
     * Generates the initial power-up for the game.
     */
    public void generateInitialPowerUp() {
        if (new Random().nextInt(100) < powerUpProbability) {
            generatePowerUp(width + spaceObstacles);
        }
    }

    /**
     * Generates a new obstacle at the specified x-coordinate.
     *
     * @param startX the x-coordinate where the obstacle should be generated
     */
    public void generateObstacle(int startX) {
        obstacles.add(new Obstacle(startX));
    }

    /**
     * Generates a new power-up at the specified x-coordinate.
     *
     * @param startX the x-coordinate where the power-up should be generated
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
     * Updates the game state and checks for collisions.
     *
     * @param e the action event that triggered this method
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver && !paused) {
            player.update();
            for (int i = obstacles.size() - 1; i >= 0; i--) {
                Obstacle obstacle = obstacles.get(i);
                obstacle.update();
                if (obstacle.isOffScreen()) {
                    obstacles.remove(i);
                    generateObstacle(width + spaceObstacles);
                }
            }
            if (powerUp != null) {
                powerUp.update();
                if (powerUp.isOffScreen()) {
                    powerUp = null;
                    generatePowerUp(width + spaceObstacles);
                }
            }
            checkCollision();
            scoreManager.increment();
            repaint();
        }
    }

    /**
     * Checks for collisions between the player and obstacles or the power-up.
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

        if (powerUp != null) {
            g.setColor(Color.BLUE);
            g.fillRect(powerUp.getBounds().x, powerUp.getBounds().y, powerUp.getBounds().width, powerUp.getBounds().height);
        }

        effects.drawScore(g, scoreManager.getScore());
        effects.drawHighScore(g, highScore);

        if (gameOver) {
            effects.drawGameOver(g, width, height);
            updateHighScore();
        } else if (paused) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("Paused", width / 2 - 100, height / 2);
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
        try (PrintWriter writer = new PrintWriter("highscoreGame2.txt")) {
            writer.println(highScore);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the high score from a file.
     */
    public void loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader("highscoreGame2.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                highScore = Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

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
     * Returns to the main menu when the 'M' key is pressed.
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
