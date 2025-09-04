package robotanarchy;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Recursos {
    public static Image cargarImagen(String ruta) {
        try {
            BufferedImage img = ImageIO.read(new File(ruta));
            return img;
        } catch (Exception e) {
            System.out.println("No se pudo cargar imagen: " + ruta + " -> " + e.getMessage());
            return null;
        }
    }
}
