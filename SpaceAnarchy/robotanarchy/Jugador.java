package robotanarchy;

import java.awt.*;

public class Jugador {
    public int x,y,w,h;
    public boolean izquierda=false, derecha=false;
    public int vidas = 3;
    private int cooldown = 0;
    private int maxCd = 12;
    private Image sprite;

    public Jugador(int x, int y) {
        this.x=x; this.y=y; this.w=52; this.h=22;
        sprite = Recursos.cargarImagen("assets/sprites/jugador.png");
    }

    public void tick() {
        if (cooldown>0) cooldown--;
    }

    public void mover(int ancho) {
        if (izquierda) x -= 6;
        if (derecha) x += 6;
        if (x < 0) x = 0;
        if (x + w > ancho) x = ancho - w;
    }

    public Disparo disparar() {
        if (cooldown>0) return null;
        cooldown = maxCd;
        return new Disparo(x + w/2 - 3, y - 10, 0, -10, true, Recursos.cargarImagen("assets/sprites/bala_jugador.png"));
    }

    public Rectangle getRect() { return new Rectangle(x,y,w,h); }

    public void dibujar(Graphics g) {
        if (sprite != null) g.drawImage(sprite, x, y, w, h, null);
        else {
            g.setColor(Color.BLUE);
            g.fillRect(x,y,w,h);
        }
    }
}
