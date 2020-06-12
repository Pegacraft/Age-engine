package engine.rendering;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

/**
 * This class can be used to import images via an path.
 */
public class Image {
    private static final HashMap<String, BufferedImage> cache = new HashMap<>();

    /**
     * This method imports an image through an path
     *
     * @param path the path you wanna import from
     * @return the image as an BufferedImage
     */
    public static BufferedImage load(String path) {
        try {
            BufferedImage image = cache.get(path);
            if (image == null) {
                image = ImageIO.read(Image.class.getResource("/" + path));
                cache.put(path, image);
            } return image;
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            System.exit(1);
            return null;
        }
    }
}
