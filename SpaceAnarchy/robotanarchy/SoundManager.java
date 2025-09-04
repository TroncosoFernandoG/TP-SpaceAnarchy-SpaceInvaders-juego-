package robotanarchy;

import javax.sound.sampled.*;
import java.io.File;

public class SoundManager {
    private static Clip musicClip = null;
    private static boolean musicOn = true;
    private static boolean sfxOn = true;

    public static void setMusicOn(boolean on) { musicOn = on; if (!musicOn) stopMusic(); }
    public static void setSfxOn(boolean on) { sfxOn = on; }

    public static void playMusic(String ruta, boolean loop) {
        if (!musicOn) return;
        try {
            stopMusic();
            File f = new File(ruta);
            AudioInputStream ais = AudioSystem.getAudioInputStream(f);
            musicClip = AudioSystem.getClip();
            musicClip.open(ais);
            if (loop) musicClip.loop(Clip.LOOP_CONTINUOUSLY);
            else musicClip.start();
        } catch (Exception e) { System.out.println("musica no disponible: " + ruta); }
    }

    public static void stopMusic() {
        if (musicClip != null) { musicClip.stop(); musicClip.close(); musicClip = null; }
    }

    public static void playSfx(String ruta) {
        if (!sfxOn) return;
        try {
            File f = new File(ruta);
            AudioInputStream ais = AudioSystem.getAudioInputStream(f);
            Clip c = AudioSystem.getClip();
            c.open(ais);
            c.start();
        } catch (Exception e) { System.out.println("sfx no disponible: " + ruta); }
    }
}
