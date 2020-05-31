package engine.rendering;

import engine.Game;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Graphics {
    public static Graphics2D g;

    /**
     * This is the loop that does the rendering. Maybe interesting for advanced programmers. But not important for normal use.
     */
    public static void GraphicsLoop() {
        Game.displays.values().forEach(e -> {
            BufferStrategy bs = e.bs();
            g = (Graphics2D) bs.getDrawGraphics();
            g.scale(1, 1);
            g.setColor(e.backGround);
            g.fillRect(0, 0, e.getWidth(), e.getHeight());
            g.scale(((float) e.getWidth()) / 1280, ((float) e.getHeight()) / 720);
            try {
                Game.scenes.get(e.getAttachedScene()).renderLoop();
            } catch (NullPointerException ignore) {
            }
            bs.show();
            g.dispose();
        });
    }
}
