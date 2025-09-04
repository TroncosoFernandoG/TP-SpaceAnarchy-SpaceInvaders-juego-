package robotanarchy;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private final CardLayout layout = new CardLayout();
    private final JPanel root = new JPanel(layout);
    private final MenuPanel menu;
    private final GamePanel game;
    private final GameOverPanel gameOver;

    public VentanaPrincipal() {
        setTitle("Robot Anarchy");
        setSize(960, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        menu = new MenuPanel(this);
        game = new GamePanel(this);
        gameOver = new GameOverPanel(this);

        root.add(menu, "menu");
        root.add(game, "game");
        root.add(gameOver, "over");

        setContentPane(root);
        setVisible(true);
        mostrarMenu();
    }

    public void mostrarMenu() {
        layout.show(root, "menu");
        menu.requestFocusInWindow();
    }

    public void mostrarJuego() {
        layout.show(root, "game");
        game.startNewGame();
        game.requestFocusInWindow();
    }

    public void mostrarGameOver(int score) {
        game.stopLoop();
        gameOver.setScore(score);
        layout.show(root, "over");
        gameOver.requestFocusInWindow();
    }
}