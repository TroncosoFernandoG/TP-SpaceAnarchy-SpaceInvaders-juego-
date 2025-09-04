package robotanarchy;

import java.awt.*;

public class Disparo {
    public int x,y,w=6,h=12;
    public int vx, vy;
    public boolean delJugador;
    private Image img;

    public Disparo(int x,int y,int vx,int vy,boolean delJugador, Image img) {
        this.x=x; this.y=y; this.vx=vx; this.vy=vy; this.delJugador=delJugador; this.img = img;
    }

    public void update() { x += vx; y += vy; }

    public Rectangle getRect() { return new Rectangle(x,y,w,h); }

    public void dibujar(Graphics g) {
        if (img != null) g.drawImage(img, x, y, w, h, null);
        else {
            g.setColor(delJugador ? Color.GREEN : Color.RED);
            g.fillRect(x,y,w,h);
        }
    }
}
