package engine.rendering;

import engine.Entity;
import engine.Game;
import engine.Scene;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

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
    private static double stdWidth = 1280d;
    private static double stdHeight = 720d;
    private static double scaleWidth = 1;
    private static double scaleHeight = 1;


    private Graphics() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * This is the loop that does the rendering. Maybe interesting for advanced programmers. But not important for normal use.
     */
    public static void graphicsLoop() {
        Game.getDisplays().values().forEach(e -> {
            BufferStrategy bs = e.bs();
            try {
                g = (Graphics2D) bs.getDrawGraphics();
                g.setRenderingHint(
                        RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g.setRenderingHint(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g.setColor(e.getCanvas().getBackground());
                g.fillRect(0, 0, e.getWidth(), e.getHeight());
                scaleWidth = e.getWidth() / stdWidth;
                scaleHeight = e.getHeight() / stdHeight;
                g.scale(scaleWidth, scaleHeight);
                Scene s = Game.getScene(e.getAttachedScene());
                if (s != null) {
                    List<Entity> queue = new ArrayList<>(s.getObjectList());
                    while (!queue.isEmpty()) {
                        Entity ent = queue.remove(0);
                        if (ent.enabled) {
                            ent.anchorToCam();
                            queue.addAll(ent.getObjectList());
                        }
                    }
                }

                Graphics.g.translate(xOffset, yOffset);
                if (s != null) {
                    try {
                        s.renderLoop();
                        renderChildren(s.getObjectList());
                    } catch (ConcurrentModificationException ignore) {
                    }
                }
                bs.show();
                g.dispose();
            } catch (IllegalStateException ignore) {
            }
        });


    }

    private static void renderChildren(List<Entity> entities) {
        for (Entity entity : entities) {
            if (entity.enabled) {
                if (entity.rotatePos == null) {
                    g.rotate(entity.rotation, entity.x, entity.y);
                    entity.renderLoop();
                    g.rotate(-entity.rotation, entity.x, entity.y);
                } else {
                    g.rotate(entity.rotation, entity.rotatePos.x, entity.rotatePos.y);
                    entity.renderLoop();
                    g.rotate(-entity.rotation, entity.rotatePos.x, entity.rotatePos.y);
                }
                renderChildren(entity.getObjectList());
            }
        }
    }

    /**
     * Use this method to offset the camera location
     *
     * @param x The x offset
     * @param y The y offset
     */
    public static void setCamPos(int x, int y) {
        xOffset = x;
        yOffset = y;
    }


    /**
     * @return returns the exact center of the screen as a <code>Point</code>.
     */
    public static Point screenCenter() {
        return new Point((int) Math.round(stdWidth / 2), (int) Math.round(stdHeight / 2));
    }

    /**
     * @return Returns the current camera position
     */
    public static Point getCamPos() {
        return new Point(xOffset, yOffset);
    }

    /**
     * @return Returns the Standard width (Original window width)
     */
    public static int getStdWidth() {
        return (int) stdWidth;
    }

    /**
     * Sets the standard width of the window (changing could break scaling)
     * @param stdWidth The new standard width
     */
    public static void setStdWidth(int stdWidth) {
        Graphics.stdWidth = stdWidth;
    }

    /**
     * @return Returns the Standard height (Original window height)
     */
    public static int getStdHeight() {
        return (int) stdHeight;
    }

    /**
     * Sets the standard height of the window (changing could break scaling)
     * @param stdHeight The new standard height
     */
    public static void setStdHeight(int stdHeight) {
        Graphics.stdHeight = stdHeight;
    }
}
