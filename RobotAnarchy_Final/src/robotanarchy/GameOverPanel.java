package robotanarchy;

import javax.swing.*;
import java.awt.*;

public class GameOverPanel extends JPanel {
    private final VentanaPrincipal ventana;
    private int score = 0;

    public GameOverPanel(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(null);
        setBackground(Color.BLACK);

        JLabel puntos = new JLabel("PUNTUACION: " + score, SwingConstants.CENTER);
        puntos.setFont(new Font("Arial", Font.BOLD, 24));
        puntos.setForeground(Color.WHITE);
        puntos.setBounds(0, 200, 960, 40);
        add(puntos);

        JButton retryBtn = new JButton("VOLVER A JUGAR");
        retryBtn.setBounds(960/2 - 100, 280, 200, 50);
        add(retryBtn);

        JButton menuBtn = new JButton("VOLVER AL MENU");
        menuBtn.setBounds(960/2 - 100, 350, 200, 50);
        add(menuBtn);

        retryBtn.addActionListener(e -> ventana.mostrarJuego());
        menuBtn.addActionListener(e -> ventana.mostrarMenu());
    }
    
    public void setScore(int s) {
        this.score = s;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        String goText = "GAME OVER";
        int goTextWidth = g.getFontMetrics().stringWidth(goText);
        g.drawString(goText, (getWidth() - goTextWidth) / 2, 100);
        
        JLabel puntos = (JLabel) getComponent(0);
        puntos.setText("PUNTUACION: " + score);
    }
}