package engine.rendering;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

/**
 * Use this class to create an animation using a tile sheet.
 */
class FrameAnimation {

    final int frameTime;
    final int imgCount;
    int i = 0;
    int currentImg = 0;
    int x, y;
    final ImageObserver obs;
    public boolean start = false;
    private final ArrayList<Image> imageList = new ArrayList<>();

    /**
     * @param tileSheet The tile sheet that should be used
     * @param frameTime The duration a image will we visible
     * @param imgCount  The number of different images in the tile sheet
     * @param scale     the scale multiplier
     * @param obs       the observer of the images
     */
    public FrameAnimation(BufferedImage tileSheet, int frameTime, int imgCount, int scale, ImageObserver obs) {
        this.obs = obs;
        this.frameTime = frameTime;
        this.imgCount = imgCount;
        int width, height;
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
    void playAnimation() {
        if (!start)
            return;
        if (i <= frameTime) {
            Graphics.g.drawImage(imageList.get(currentImg), x, y, obs);
            i++;
        }
        if (i > frameTime) {
            currentImg++;
            i = 0;
        }
        if (currentImg >= imgCount) {
            start = false;
            currentImg = 0;
        }
    }
}
