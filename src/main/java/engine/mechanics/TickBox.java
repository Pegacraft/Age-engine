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
    private final Scene scene;
    private boolean ticked = false;

    /**
     * This is a simple tick box. It is used to get a true or false output from the user.
     *
     * @param x      The x position.
     * @param y      The y position.
     * @param width  The width of the box.
     * @param height The height of the box
     * @param scene  The scene the box is attached to.
     */
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

    /**
     * Sets the boarder color of the tick box.
     *
     * @param color The color it should be set to.
     */
    public TickBox setBorderColor(Color color) {
        this.borderColor = color;
        return this;
    }

    /**
     * Sets the state of the tick box to true or false.
     *
     * @param ticked The state you want it to be set.
     */
    public TickBox setTicked(boolean ticked) {
        this.ticked = ticked;
        return this;
    }

    /**
     * Sets the x position of the tick box.
     *
     * @param x The x position
     */
    public TickBox setX(int x) {
        this.x = x;
        return this;
    }

    /**
     * Sets the y position of the tick box.
     *
     * @param y The y position
     */
    public TickBox setY(int y) {
        this.y = y;
        return this;
    }

    /**
     * Sets the width of the tick box.
     *
     * @param width The width it should be set at.
     */
    public TickBox setWidth(int width) {
        this.width = width;
        return this;
    }

    /**
     * Sets the height of the tick box.
     *
     * @param height The height it should be set at.
     */
    public TickBox setHeight(int height) {
        this.height = height;
        return this;
    }

    /**
     * Sets the color of the tick itself.
     *
     * @param color The color it should be set at.
     */
    public TickBox setTickColor(Color color) {
        tickColor = color;
        return this;
    }

    /**
     * @return returns if the tick box is ticked or not.
     */
    public boolean isTicked() {
        return ticked;
    }
}
