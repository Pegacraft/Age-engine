package engine.rendering;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

/**
 * Use this class to create an animation using a tile sheet.
 */
public class Animation {

    private ArrayList<Image> imageList = new ArrayList<java.awt.Image>();
    int frameTime;
    int i = 0;
    int currentImg = 0;
    int x, y;
    int imgCount;
    ImageObserver obs;

    /**
     * @param tileSheet The tile sheet that should be used
     * @param frameTime The duration a image will we visible
     * @param imgCount  The number of different images in the tile sheet
     * @param x         the x position where you wanna show the animation
     * @param y         the y position where you wanna show the animation
     * @param scale     the scale multiplier
     * @param obs       the observer of the images
     */
    public Animation(BufferedImage tileSheet, int frameTime, int imgCount, int x, int y, int scale, ImageObserver obs) {
        this.x = x;
        this.y = y;
        this.obs = obs;
        this.frameTime = frameTime;
        this.imgCount = imgCount;
        int width = 0, height = 0;
        int yS = 0;
        width = tileSheet.getWidth();
        height = tileSheet.getHeight() / imgCount;

        for (int i = 0; i < imgCount; i++) {
            imageList.add(tileSheet.getSubimage(0, yS, width, height).getScaledInstance(width * scale, height * scale, 3));
            yS += height;
        }
    }

    /**
     * put this method in the renderLoop to start showing the animation
     */
    public void playAnimation() {
        if (i <= frameTime) {
            Graphics.g.drawImage(imageList.get(currentImg), x, y, obs);
            i++;
        }
        if (i > frameTime) {
            currentImg++;
            i = 0;
        }
        if (currentImg >= imgCount)
            currentImg = 0;
    }
}
