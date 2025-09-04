package robotanarchy;

import java.util.ArrayList;
import java.util.List;

public class Niveles {
    public static int dirGlobal = 1;

    public static class ConfigNivel {
        public int filas, cols, tipo, vidasEnemigo;
        public ConfigNivel(int f,int c,int t,int v){ filas=f; cols=c; tipo=t; vidasEnemigo=v; }
    }

    public static List<ConfigNivel> niveles() {
        List<ConfigNivel> L = new ArrayList<>();
        L.add(new ConfigNivel(4,8,Enemigo.NORMAL,1));
        L.add(new ConfigNivel(4,9,Enemigo.NORMAL,1));
        L.add(new ConfigNivel(5,10,Enemigo.NORMAL,2));
        L.add(new ConfigNivel(4,8,Enemigo.KAMIKAZE,1));
        L.add(new ConfigNivel(4,9,Enemigo.KAMIKAZE,1));
        L.add(new ConfigNivel(4,8,Enemigo.TIRADOR,1));
        L.add(new ConfigNivel(5,10,Enemigo.TIRADOR,2));
        L.add(new ConfigNivel(5,10,Enemigo.NORMAL,2));
        L.add(new ConfigNivel(5,10,Enemigo.KAMIKAZE,2));
        L.add(new ConfigNivel(6,11,Enemigo.TIRADOR,2));
        L.add(new ConfigNivel(6,11,Enemigo.NORMAL,3));
        return L;
    }
}
