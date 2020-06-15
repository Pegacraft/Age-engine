package engine.rendering;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

/**
 * Use this class to create an animation using a tile sheet.
 */
class FrameAnimation {

    private final int frameTime;
    private final int imgCount;
    private final ImageObserver obs;
    private final ArrayList<Image> imageList = new ArrayList<>();
    private int i = 0;
    private int currentImg = 0;
    private int x;
    private int y;
    private boolean start = false;

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
        int width = tileSheet.getWidth();
        int height = tileSheet.getHeight() / imgCount;
        for (int yS = 0; yS < imgCount * height; yS += height)
            imageList.add(tileSheet.getSubimage(0, yS, width, height)
                    .getScaledInstance(width * scale, height * scale, 3));
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

    void setX(int x) {
        this.x = x;
    }

    void setY(int y) {
        this.y = y;
    }

    void start() {
        start = true;
    }

    void stop() {
        start = false;
    }

    void setCurrentImg(int currentImg) {
        this.currentImg = currentImg;
    }
}
