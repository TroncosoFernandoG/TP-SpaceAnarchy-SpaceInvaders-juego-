package robotanarchy;
import javax.swing.*;
import java.awt.*;
public class VentanaPrincipal extends JFrame {
    CardLayout cl = new CardLayout();
    JPanel mainPanel = new JPanel(cl);
    MenuPanel menu = new MenuPanel(this);
    GamePanel game = new GamePanel(this);
    GameOverPanel gameOver = new GameOverPanel(this);
    public VentanaPrincipal(){
        setTitle("Robot Anarchy");
        setSize(Constants.WIDTH, Constants.HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        mainPanel.add(menu,"menu");
        mainPanel.add(game,"game");
        mainPanel.add(gameOver,"over");
        add(mainPanel);
        setVisible(true);
    }
    public void mostrar(String name){
        if(name.equals("game")) game.reset();
        cl.show(mainPanel, name);
    }
}