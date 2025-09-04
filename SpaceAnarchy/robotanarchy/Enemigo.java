package robotanarchy;

import java.awt.*;
import java.util.Random;

public class Enemigo {
    public static final int NORMAL=0, KAMIKAZE=1, TIRADOR=2;
    public int x,y,w=32,h=22;
    public int tipo=0;
    public int vida=1;
    public boolean viva=true;
    public boolean enFormacion=true;
    public int velGrupo = 3;
    public int objetivoX=0;
    public int velX=0, velY=0;
    public int pasoBajada = 14;
    private Random rng = new Random();

    private Image spriteA, spriteB; // 2-frame animation

    public Enemigo(int x,int y,int tipo,int vida) {
        this.x=x; this.y=y; this.tipo=tipo; this.vida=vida;
        cargaSprites();
        if (tipo==NORMAL) { velGrupo = 3; pasoBajada = 12; }
        if (tipo==KAMIKAZE) { velGrupo = 4; pasoBajada = 10; }
        if (tipo==TIRADOR) { velGrupo = 3; pasoBajada = 14; }
    }

    private void cargaSprites() {
        try {
            if (tipo==NORMAL) {
                spriteA = Recursos.cargarImagen("assets/sprites/enemigo_azul_0.png");
                spriteB = Recursos.cargarImagen("assets/sprites/enemigo_azul_1.png");
            } else if (tipo==KAMIKAZE) {
                spriteA = Recursos.cargarImagen("assets/sprites/enemigo_rojo_0.png");
                spriteB = Recursos.cargarImagen("assets/sprites/enemigo_rojo_1.png");
            } else {
                spriteA = Recursos.cargarImagen("assets/sprites/enemigo_verde_0.png");
                spriteB = Recursos.cargarImagen("assets/sprites/enemigo_verde_1.png");
            }
        } catch (Exception e) { spriteA = spriteB = null; }
    }

    private int animTick = 0;
    public void update() {
        x += velX; y += velY;
        if (Math.abs(velX) > 0 || Math.abs(velY)>0) {
            // movimiento en vuelo kamikaze
        }
    }

    public Disparo intentarDisparar() {
        if (tipo != TIRADOR) return null;
        if (rng.nextInt(100) < 2) {
            return new Disparo(x + w/2 - 3, y + h + 6, 0, 6, false,
                    Recursos.cargarImagen("assets/sprites/bala_enemigo.png"));
        }
        return null;
    }

    public void recibirDaño(int d) {
        vida -= d;
        if (vida <= 0) { viva = false; }
    }

    public Rectangle getRect() { return new Rectangle(x,y,w,h); }

    public String getSonidoMuerte() {
        if (tipo==NORMAL) return "assets/sonidos/muerte_azul.wav";
        if (tipo==KAMIKAZE) return "assets/sonidos/muerte_rojo.wav";
        return "assets/sonidos/muerte_verde.wav";
    }

    public void dibujar(Graphics g) {
        Image spr = (animTick/20)%2==0 ? spriteA : spriteB;
        animTick++;
        if (spr != null) g.drawImage(spr, x, y, w, h, null);
        else {
            g.setColor(Color.GRAY);
            g.fillRect(x,y,w,h);
        }
    }
}
