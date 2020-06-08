package engine.rendering;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Image {

    public static BufferedImage load(String path) {
        try {
            return ImageIO.read(Image.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
