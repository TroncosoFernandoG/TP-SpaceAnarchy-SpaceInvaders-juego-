package robotanarchy;
import javax.swing.*;
import java.awt.*;
public class MenuPanel extends JPanel {
    VentanaPrincipal vp;
    JButton jugarBtn;
    public MenuPanel(VentanaPrincipal vp){
        this.vp = vp;
        setLayout(null);
        jugarBtn = new JButton("JUGAR");
        jugarBtn.setBounds(Constants.WIDTH/2-Constants.BUTTON_WIDTH/2,
                300, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT);
        jugarBtn.addActionListener(e -> vp.mostrar("game"));
        add(jugarBtn);
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        ImageIcon fondo = new ImageIcon("assets/fondos/menu.png");
        g.drawImage(fondo.getImage(),0,0,getWidth(),getHeight(),this);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.BOLD,48));
        String titulo = "ROBOT ANARCHY";
        int titleY = 150;
        int strW = g.getFontMetrics().stringWidth(titulo);
        g.drawString(titulo, getWidth()/2 - strW/2, titleY);
    }
}