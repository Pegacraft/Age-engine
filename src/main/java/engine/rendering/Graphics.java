package engine.rendering;

import engine.Game;
import engine.Object;
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

    /**
     * This is the loop that does the rendering. Maybe interesting for advanced programmers. But not important for normal use.
     */
    public static void GraphicsLoop() {
        Game.displays.values().forEach(e -> {
            BufferStrategy bs = e.bs();
            g = (Graphics2D) bs.getDrawGraphics();
            g.setColor(e.backGround);
            g.fillRect(0, 0, e.getWidth(), e.getHeight());
            g.scale(((float) e.getWidth()) / 1280, ((float) e.getHeight()) / 720);
            Scene s = Game.scenes.get(e.getAttachedScene());
            if (s != null) {
                s.renderLoop();
                s.getObjectList().forEach(Object::renderLoop);
            } else throw new IllegalStateException("no state defined for that display");
            bs.show();
            g.dispose();
        });
    }
}
