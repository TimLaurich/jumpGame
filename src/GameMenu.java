import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu extends JPanel implements ActionListener {
    private JFrame frame;
    private JButton easyButton, hardButton;


    public GameMenu(JFrame frame) {
        this.frame = frame;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Select Difficulty");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLabel, gbc);

        easyButton = new JButton("Easy");
        easyButton.setFont(new Font("Arial", Font.BOLD, 20));
        easyButton.addActionListener(this);
        gbc.gridy = 1;
        add(easyButton, gbc);

        hardButton = new JButton("Hard");
        hardButton.setFont(new Font("Arial", Font.BOLD, 20));
        hardButton.addActionListener(this);
        gbc.gridy = 2;
        add(hardButton, gbc);
    }

    /**
     * Handles button clicks in the game menu.
     *
     * @param e the action event that triggered this method
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == easyButton) {
            startGame(new Game());
        } else if (e.getSource() == hardButton) {
            startGame(new Game2());
        }
    }

    /**
     * Starts a new game with the given game panel.
     *
     * @param game the game panel to start
     */
    private void startGame(JPanel game) {
        frame.getContentPane().removeAll();
        frame.add(game);
        frame.revalidate();
        frame.repaint();
        game.requestFocusInWindow();
    }
}