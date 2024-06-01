import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
    }

    @Test
    void loadHighScore() {

        try (PrintWriter writer = new PrintWriter("highscore.txt")) {
            writer.println(100);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        game.loadHighScore();
        assertEquals(100, game.getHighScore());
    }


    @Test
    void paintComponent() {
        Graphics g = new JPanel().getGraphics();
        try {
            game.paintComponent(g);
        } catch (Exception e) {

        }
    }

    @Test
    void returnToMenu() {
        JFrame frame = new JFrame();
        frame.add(game);
        frame.setVisible(true);

        game.returnToMenu();
        assertTrue(frame.getContentPane().getComponent(0) instanceof GameMenu);

        frame.dispose();
    }
}
