package engine.editor.menu;

import engine.Object;
import engine.Scene;
import engine.listeners.MouseButtons;
import engine.mechanics.Hitbox;
import engine.rendering.Image;

import java.awt.*;
import java.awt.image.BufferedImage;

import static engine.rendering.Graphics.g;

public class Sprite extends Object {
    int x, y;
    double scale;
    BufferedImage sprite;
    String spritePath;
    java.awt.Image spriteScaled;
    private Hitbox h;
    public boolean delete = false;

    public Sprite(int x, int y, double scale, String sprite, Scene scene) {
        this.scale = scale;
        this.x = x;
        this.y = y;
        spritePath = sprite;
        this.sprite = Image.load(sprite);
        spriteScaled = this.sprite.getScaledInstance((int) (this.sprite.getWidth() * scale),
                (int) (this.sprite.getHeight() * scale), 3);

        h = new Hitbox(new Point(x, y), new Point(x + this.sprite.getWidth(), y + this.sprite.getHeight()));

        scene.mouseListener.addEvent(MouseButtons.RIGHT_DOWN, e -> {
            if (h.isInside(scene.mouseListener.getMousePos())) {
                System.out.println("test");
                delete = true;
                return true;
            }
            return false;
        });
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
