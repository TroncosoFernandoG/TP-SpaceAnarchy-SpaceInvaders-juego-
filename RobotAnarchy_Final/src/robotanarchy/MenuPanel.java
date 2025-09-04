package robotanarchy;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private final VentanaPrincipal ventana;

    public MenuPanel(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(null);

        JLabel titulo = new JLabel("ROBOT ANARCHY", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 48));
        titulo.setForeground(Color.WHITE);
        titulo.setBounds(0, 100, 960, 60);
        add(titulo);

        JButton jugarBtn = new JButton("JUGAR");
        jugarBtn.setBounds(960/2 - 75, 250, 150, 50);
        add(jugarBtn);

        jugarBtn.addActionListener(e -> ventana.mostrarJuego());
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(30, 30, 50));
        g.fillRect(0, 0, getWidth(), getHeight());
    }
}