package engine.mechanics;

import engine.Entity;
import engine.Scene;
import engine.listeners.MouseButtons;

import java.awt.*;

import static engine.rendering.Graphics.g;

public class TickBox implements Entity {

    private int x, y, width, height;
    private Color borderColor = Color.black;
    private Color tickColor = Color.green;
    private Hitbox clickArea;
    private Scene scene;
    private boolean ticked = false;

    public TickBox(int x, int y, int width, int height, Scene scene) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scene = scene;
    }

    @Override
    public void init() {
        clickArea = new Hitbox(new Point(x, y), new Point(x + width, y + height));
        scene.mouseListener.addEvent(MouseButtons.LEFT_DOWN, e -> {
            if (clickArea.isInside(scene.mouseListener.getMousePos())) {
                ticked = !ticked;
            }
        }, false);

    }

    @Override
    public void logicLoop() {

    }

    @Override
    public void renderLoop() {
        if (ticked) {
            g.setColor(tickColor);
            g.fillRect(x, y, width, height);
        }
        g.setColor(borderColor);
        g.drawRect(x, y, width, height);
    }

    public TickBox setBorderColor(Color color) {
        this.borderColor = color;
        return this;
    }

    public TickBox setTicked(boolean ticked) {
        this.ticked = ticked;
        return this;
    }

    public TickBox setTickColor(Color color) {
        tickColor = color;
        return this;
    }

    public boolean isTicked() {
        return ticked;
    }
}
