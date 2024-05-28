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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == easyButton) {
            frame.getContentPane().removeAll();
            Game game = new Game();
            frame.add(game);
            frame.revalidate();
            frame.repaint();
            game.requestFocusInWindow();
        } else if (e.getSource() == hardButton) {
            frame.getContentPane().removeAll();
            Game2 game2 = new Game2();
            frame.add(game2);
            frame.revalidate();
            frame.repaint();
            game2.requestFocusInWindow();
        }
    }


}
