package engine.rendering;

import engine.Entity;
import engine.Game;
import engine.Scene;

import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * This is the <code>Graphics</code> class. It is used for everything that has to do with graphics.
 * E. g. if you want to draw a rectangle, you can achieve it in the following way <code>Graphics.g.fillRect(x,y,width,height);</code>
 * <p>The <code>g</code> is the normal <code>Graphics2D</code> object.</p>
 */
public class Graphics {
    /**
     * the normal Graphics2D object.
     */
    public static Graphics2D g;
    private static int xOffset = 0;
    private static int yOffset = 0;

    private Graphics() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * This is the loop that does the rendering. Maybe interesting for advanced programmers. But not important for normal use.
     */
    public static void graphicsLoop() {
        Game.getDisplays().values().forEach(e -> {
            BufferStrategy bs = e.bs();
            g = (Graphics2D) bs.getDrawGraphics();
            g.setColor(Color.white);
            g.fillRect(0, 0, e.getWidth(), e.getHeight());
            g.scale(e.getWidth() / 1280d, e.getHeight() / 720d);
            Graphics.g.translate(xOffset, yOffset);
            Scene s = Game.getScene(e.getAttachedScene());
            if (s != null) {
                s.getObjectList().forEach(Entity::renderLoop);
                s.renderLoop();
            } else throw new IllegalStateException("no state defined for that display");
            Animation.animationLoop();
            bs.show();
            g.dispose();
        });
    }

    /**
     * Use this method to offset the camera location
     *
     * @param x The x offset
     * @param y The y offset
     */
    public static void moveCam(int x, int y) {
        xOffset = x;
        yOffset = -y;
    }

    /**
     * @return Returns the current offset
     */
    public static Point getCamPos() {
        return new Point(xOffset, yOffset);
    }
}
