package engine.rendering;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * This class can be used to import images via an path.
 */
public class Image {
    /**
     * This method imports an image through an path
     *
     * @param path the path you wanna import from
     * @return the image as an BufferedImage
     */
    public static BufferedImage load(String path) {
        try {
            return ImageIO.read(Image.class.getResource("/" + path));
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }
}
