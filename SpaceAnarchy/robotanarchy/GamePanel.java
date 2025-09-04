package robotanarchy;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
public class GamePanel extends JPanel implements Runnable, KeyListener {
    VentanaPrincipal vp;
    Thread hilo;
    boolean running = false;
    int puntos = 0;
    int vidas = 3;
    java.util.List<Rectangle> enemigos = new ArrayList<>();
    java.util.List<Rectangle> disparos = new ArrayList<>();
    public GamePanel(VentanaPrincipal vp){
        this.vp = vp;
        setFocusable(true);
        addKeyListener(this);
    }
    public void reset(){
        puntos = 0;
        vidas = 3;
        enemigos.clear();
        disparos.clear();
        for(int i=0;i<15;i++){
            enemigos.add(new Rectangle(100+(i%5)*100,100+(i/5)*60,40,30));
        }
        running = true;
        hilo = new Thread(this);
        hilo.start();
        requestFocusInWindow();
    }
    @Override
    public void run(){
        while(running){
            updateGame();
            repaint();
            try{Thread.sleep(30);}catch(Exception e){}
        }
    }
    public void updateGame(){
        for(int i=0;i<disparos.size();i++){
            Rectangle d = disparos.get(i);
            d.y -= 10;
            if(d.y<0) disparos.remove(i--);
        }
        for(int i=0;i<disparos.size();i++){
            Rectangle d = disparos.get(i);
            for(int j=0;j<enemigos.size();j++){
                Rectangle en = enemigos.get(j);
                if(d.intersects(en)){
                    disparos.remove(i--);
                    enemigos.remove(j--);
                    puntos+=100;
                    break;
                }
            }
        }
        if(enemigos.isEmpty()){
            running = false;
            vp.mostrar("over");
        }
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        ImageIcon fondo = new ImageIcon("assets/fondos/galaxia.png");
        g.drawImage(fondo.getImage(),0,0,getWidth(),getHeight(),this);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.BOLD,20));
        String hud = "PUNTOS: "+puntos;
        int strW = g.getFontMetrics().stringWidth(hud);
        g.drawString(hud, getWidth()/2-strW/2, 30);
        String vid = "VIDAS: ";
        for(int i=0;i<vidas;i++) vid+="█ ";
        g.drawString(vid,getWidth()-150,30);
        g.setColor(Color.RED);
        for(Rectangle en: enemigos) g.fillRect(en.x,en.y,en.width,en.height);
        g.setColor(Color.YELLOW);
        for(Rectangle d: disparos) g.fillRect(d.x,d.y,d.width,d.height);
    }
    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            disparos.add(new Rectangle(Constants.WIDTH/2-2,Constants.HEIGHT-60,5,15));
        }
    }
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
}