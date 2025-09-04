package robotanarchy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements Runnable, KeyListener {
    private final VentanaPrincipal ventana;
    private Thread gameThread;
    private volatile boolean running = false;
    private int score = 0;
    private int lives = 3;
    private final Random random = new Random();

    private int px, py, pw = 40, ph = 20;
    private boolean left, right;
    
    private List<Enemy> enemigos = new ArrayList<>();
    private List<Disparo> disparosJugador = new ArrayList<>();
    private List<Disparo> disparosEnemigos = new ArrayList<>();
    private int nivel = 1;

    public GamePanel(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setFocusable(true);
        setBackground(Color.BLACK);
        addKeyListener(this);
    }

    public void startNewGame() {
        score = 0;
        lives = 3;
        nivel = 1;
        px = getWidth() / 2 - pw / 2;
        py = getHeight() - 60;
        disparosJugador.clear();
        disparosEnemigos.clear();
        enemigos.clear();
        crearNivel(nivel);
        startLoop();
    }
    
    private void restartLevel() {
        lives--;
        if (lives <= 0) {
            stopLoop();
            SwingUtilities.invokeLater(() -> ventana.mostrarGameOver(score));
        } else {
            px = getWidth() / 2 - pw / 2;
            py = getHeight() - 60;
            disparosJugador.clear();
            disparosEnemigos.clear();
            enemigos.clear();
            crearNivel(nivel);
        }
    }
    
    private void startLoop() {
        stopLoop();
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    public void stopLoop() {
        running = false;
        if (gameThread != null && gameThread.isAlive()) {
            try { gameThread.join(); } catch (InterruptedException ignored) {}
        }
    }

    private void crearNivel(int n) {
        enemigos.clear();
        int filas = 2 + (n / 2);
        if (filas > 5) filas = 5;
        
        int cols = 5 + n;
        if (cols > 15) cols = 15;
        
        int attackEnemyCount = 0;
        int shooterEnemyCount = 0;
        
        for (int row = 0; row < filas; row++) {
            for (int col = 0; col < cols; col++) {
                int tipo = 1; 
                if (n >= 3 && n <= 4) {
                    if (random.nextInt(10) < 2 && attackEnemyCount < 5) { 
                        tipo = 2;
                        attackEnemyCount++;
                    }
                } else if (n >= 5 && n <= 6) {
                    if (random.nextInt(10) < 3) {
                        int randomType = random.nextInt(2) + 2;
                        if(randomType == 2 && attackEnemyCount < 5) {
                            tipo = 2;
                            attackEnemyCount++;
                        } else if (randomType == 3 && shooterEnemyCount < 5) {
                            tipo = 3;
                            shooterEnemyCount++;
                        }
                    }
                } else if (n > 6) { 
                    if (random.nextInt(10) < 5) { 
                        int randomType = random.nextInt(2) + 2;
                        if(randomType == 2 && attackEnemyCount < 5) {
                            tipo = 2;
                            attackEnemyCount++;
                        } else if (randomType == 3 && shooterEnemyCount < 5) {
                            tipo = 3;
                            shooterEnemyCount++;
                        }
                    }
                }
                Enemy e = new Enemy(50 + col * 60, 50 + row * 40, tipo);
                enemigos.add(e);
            }
        }
    }

    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                if(!running) return;
                updateGame();
                delta--;
            }
            repaint();
            try { Thread.sleep(2); } catch (InterruptedException e) {}
        }
    }

    private void updateGame(){
        // mover jugador
        if (left) px -= 4;
        if (right) px += 4;
        if (px < 0) px = 0;
        if (px + pw > getWidth()) px = getWidth() - pw;
        
        // mover disparos del jugador
        Iterator<Disparo> itDJ = disparosJugador.iterator();
        while(itDJ.hasNext()){
            Disparo d = itDJ.next();
            d.y += d.speed;
            if(d.y < 0) itDJ.remove();
        }

        // mover y disparar enemigos
        boolean tocaborde = false;
        for (Enemy e : new ArrayList<>(enemigos)) {
            if (e.tipo == 1 || e.tipo == 3) {
                e.x += e.dir * e.vel;
                if (e.x < 8 || e.x + e.w > getWidth()-8) tocaborde = true;
                if (e.tipo == 3 && random.nextInt(100) < 1) { 
                    disparosEnemigos.add(new Disparo(e.x + e.w / 2 - 2, e.y + e.h, 5));
                }
            } else if (e.tipo == 2) {
                e.y += e.vel; 
            }
            
            // Si el enemigo toca la parte inferior
            if (e.y + e.h >= getHeight()) {
                restartLevel();
                return;
            }
        }
        
        if (tocaborde) {
            for (Enemy e : new ArrayList<>(enemigos)) {
                if(e.tipo == 1 || e.tipo == 3){
                    e.dir *= -1;
                    e.y += 12;
                }
            }
        }

        // colisiones de disparos del jugador
        Iterator<Disparo> itDJ_colision = disparosJugador.iterator();
        while(itDJ_colision.hasNext()){
            Disparo d = itDJ_colision.next();
            Iterator<Enemy> itE = enemigos.iterator();
            while(itE.hasNext()){
                Enemy e = itE.next();
                if(new Rectangle(d.x, d.y, 5, 10).intersects(e.getRect())){
                    e.vida--;
                    itDJ_colision.remove();
                    if(e.vida <= 0){
                        score += 100 * e.tipo;
                        itE.remove();
                    }
                    break;
                }
            }
        }

        // colisiones de disparos enemigos con el jugador
        Iterator<Disparo> itDE = disparosEnemigos.iterator();
        while(itDE.hasNext()){
            Disparo d = itDE.next();
            d.y += d.speed; 
            if(new Rectangle(px, py, pw, ph).intersects(new Rectangle(d.x, d.y, 5, 10))){
                itDE.remove();
                restartLevel();
                return;
            }
        }
        
        // colisión de enemigos con el jugador
        for (Enemy e : new ArrayList<>(enemigos)) {
             if(new Rectangle(px, py, pw, ph).intersects(e.getRect())){
                restartLevel();
                return;
            }
        }
        
        // siguiente nivel
        if(enemigos.isEmpty()){
            nivel++;
            crearNivel(nivel);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("PUNTUACION: " + score, getWidth()/2 - 100, 30);
        g.drawString("VIDAS: " + lives, getWidth() - 150, 30);
        g.drawString("NIVEL: " + nivel, 50, 30); 

        g.setColor(Color.CYAN);
        g.fillRect(px, py, pw, ph);

        g.setColor(Color.YELLOW);
        for(Disparo d : disparosJugador){
            g.fillRect(d.x, d.y, 5, 10);
        }
        
        g.setColor(Color.RED);
        for(Disparo d : disparosEnemigos){
            g.fillRect(d.x, d.y, 5, 10);
        }

        for(Enemy e : enemigos){
            e.dibujar(g);
        }
    }
    
    private void disparar(){
        disparosJugador.add(new Disparo(px + pw/2 - 2, py, -10));
    }

    @Override public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;
        if(e.getKeyCode() == KeyEvent.VK_SPACE) disparar();
    }
    @Override public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
    }
    @Override public void keyTyped(KeyEvent e) {}
}