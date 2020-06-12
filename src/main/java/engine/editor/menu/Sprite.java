package engine.editor.menu;

import engine.Object;
import engine.Scene;
import engine.listeners.MouseButtons;
import engine.mechanics.Hitbox;
import engine.rendering.Image;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.function.Function;

import static engine.rendering.Graphics.g;

public class Sprite extends Object {
    public final Function<MouseEvent, Boolean> onClickEvent;
    private final int x;
    private final int y;
    private final java.awt.Image spriteScaled;
    private final Hitbox h;
    public boolean delete = false;

    /**
     * create a sprite at a given location using a position, a scale,a path to an image, and a scene to be bound to.
     *
     * @param x          the x coordinate of the sprite
     * @param y          the y coordinate of the sprite
     * @param scale      the sprite's scaling factor
     * @param spritePath a path to an image
     * @param scene      the scene to be bound to
     */
    public Sprite(int x, int y, double scale, String spritePath, Scene scene) {
        this.x = x;
        this.y = y;
        BufferedImage sprite = Image.load(spritePath);
        spriteScaled = sprite.getScaledInstance((int) (sprite.getWidth() * scale),
                (int) (sprite.getHeight() * scale), 3);

        h = new Hitbox(new Point(x, y), new Point(x + sprite.getWidth(), y + sprite.getHeight()));
        onClickEvent = e -> {
            if (h.isInside(scene.mouseListener.getMousePos())) {
                delete = true;
                return true;
            }
            return false;
        };
        scene.mouseListener.addEvent(MouseButtons.RIGHT_DOWN, onClickEvent);
    }

    @Override
    public void init() {
    }

    @Override
    public void logicLoop() {
    }

    @Override
    public void renderLoop() {
        g.drawImage(spriteScaled, x, y, null);
    }
}
