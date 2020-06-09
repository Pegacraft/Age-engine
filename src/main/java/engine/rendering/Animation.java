package engine.rendering;

import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.HashMap;

/**
 * Use this method to do everything that has to do with animations.
 */
public class Animation {
    private static HashMap<String, FrameAnimation> animations = new HashMap<>();
    private static int x, y;

    /**
     * Use this method to create a new animation.
     *
     * @param tileSheet The tile sheet that should be used
     * @param frameTime The duration a image will we visible
     * @param imgCount  The number of different images in the tile sheet
     * @param scale     the scale multiplier
     * @param obs       the observer of the images
     * @param alias     The name of the animation you wanna create
     */
    public static void createAnimation(BufferedImage tileSheet, int frameTime, int imgCount, int scale, ImageObserver obs, String alias) {
        animations.put(alias, new FrameAnimation(tileSheet, frameTime, imgCount, scale, obs));
    }

    /**
     * Use this method to play an animation
     *
     * @param alias The animation name you wanna play.
     * @param x     The x position you want it to be played.
     * @param y     The y position you want it to be played.
     */
    public static void play(String alias, int x, int y) {
        animations.get(alias).x = x;
        animations.get(alias).y = y;
        animations.get(alias).start = true;
    }

    /**
     * This method stops a playing animation.
     *
     * @param alias The animation you wanna stop
     */
    public static void stop(String alias) {
        System.out.println("test");
        animations.get(alias).start = false;
        animations.get(alias).currentImg = 0;
    }

    /**
     * Use this method to update the position of a animation, that is only played once.
     *
     * @param x The x position you want it to be played.
     * @param y The y position you want it to be played.
     */
    public static void updatePos(String alias, int x, int y) {
        animations.get(alias).x = x;
        animations.get(alias).y = y;
    }

    /**
     * Internal function.
     */
    public static void animationLoop() {
        animations.values().forEach(FrameAnimation::playAnimation);
    }
}
