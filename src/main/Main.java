package main;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Treasure Hunt");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // pack() causes this window to be sized to the preferred size amd layouts of its subcomponents (= GamePanel)
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setUpGame();
        gamePanel.startGameThread();
    }
}
