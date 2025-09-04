package robotanarchy;

import java.awt.*;

public class Enemy {
    public int x, y, w = 40, h = 20;
    public int tipo;
    public int vida;
    public int dir = 1;
    public float vel = 1f;

    public Enemy(int x, int y, int tipo) {
        this.x = x;
        this.y = y;
        this.tipo = tipo;
        this.vida = tipo;
        if (tipo == 2) {
            this.vel = 1.2f; 
        }
    }
    
    public Rectangle getRect() { return new Rectangle(x, y, w, h); }

    public void dibujar(Graphics g) {
        if (tipo == 1) g.setColor(Color.RED);
        else if (tipo == 2) g.setColor(Color.BLUE);
        else if (tipo == 3) g.setColor(Color.GREEN);
        else g.setColor(Color.MAGENTA); 
        g.fillRect(x, y, w, h);
    }
}