package oopp.team16;

import javax.swing.*;
import java.awt.*;

public class GameView {

    JFrame frame = new JFrame();
    JLabel label = new JLabel();

    public GameView() {

        label.setBounds(0, 0, 100, 50);
        label.setFont(new Font("Arial", Font.PLAIN, 25));
        frame.add(label, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 420);
        frame.setTitle("UNO - Ingame");
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);
    }
}
