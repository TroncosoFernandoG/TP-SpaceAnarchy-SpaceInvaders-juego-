package robotanarchy;
import javax.swing.*;
import java.awt.*;
public class GameOverPanel extends JPanel {
    VentanaPrincipal vp;
    JButton restartBtn, menuBtn;
    public GameOverPanel(VentanaPrincipal vp){
        this.vp = vp;
        setLayout(null);
        restartBtn = new JButton("REINICIAR");
        restartBtn.setBounds(Constants.WIDTH/2-Constants.BUTTON_WIDTH/2,
                250, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        restartBtn.addActionListener(e -> vp.mostrar("game"));
        menuBtn = new JButton("MENU");
        menuBtn.setBounds(Constants.WIDTH/2-Constants.BUTTON_WIDTH/2,
                320, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        menuBtn.addActionListener(e -> vp.mostrar("menu"));
        add(restartBtn);
        add(menuBtn);
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(Color.RED);
        g.setFont(new Font("Arial",Font.BOLD,64));
        String titulo = "GAME OVER";
        int strW = g.getFontMetrics().stringWidth(titulo);
        g.drawString(titulo, getWidth()/2-strW/2,150);
    }
}