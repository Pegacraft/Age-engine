package engine.rendering;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * This class is used to extract the sub images out of an tile sheet.
 */
public class TileSheet {

    public enum READ_DIRECTION {
        UP_DOWN,
        LEFT_RIGHT;
    }

    BufferedImage sheet;
    int width, height, xImages, yImages;
    READ_DIRECTION read_direction;
    ArrayList<BufferedImage> subImages = new ArrayList<>();

    /**
     * @param sheet the tile sheet you want to process
     * @param xImages how many images are in the x dimension
     * @param yImages how many images are in the y dimension
     * @param read_direction in witch direction the tile sheet should be read
     */
    public TileSheet(BufferedImage sheet, int xImages, int yImages, READ_DIRECTION read_direction) {
        this.sheet = sheet;
        this.width = sheet.getWidth();
        this.height = sheet.getHeight();
        this.xImages = xImages;
        this.yImages = yImages;
        this.read_direction = read_direction;
        createSubImages();
    }

    void createSubImages() {
        switch (read_direction) {

            case UP_DOWN -> {
                for (int x = 0; x < xImages; x++) {
                    for (int y = 0; y < yImages; y++) {
                        subImages.add(sheet.getSubimage(x * (width / xImages), y * (height / yImages), width / xImages, height / yImages));
                    }
                }
            }
            case LEFT_RIGHT -> {
                for (int y = 0; y < yImages; y++) {
                    for (int x = 0; x < xImages; x++) {
                        subImages.add(sheet.getSubimage(x * (width / xImages), y * (height / yImages), width / xImages, height / yImages));
                    }
                }
            }
        }
    }

    /**
     * @return Returns an arrayList of the sub images.
     */
    public ArrayList<BufferedImage> getSubImages() {
        return subImages;
    }
}
