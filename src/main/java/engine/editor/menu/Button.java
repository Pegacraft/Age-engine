package engine.editor.menu;

import engine.Entity;
import engine.Game;
import engine.listeners.Mouse;
import engine.listeners.MouseButtons;
import engine.mechanics.Hitbox;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Consumer;

import static engine.rendering.Graphics.g;

public class Button implements Entity {
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final Mouse mouseListener = Game.getScene("Game").mouseListener;
    private final Hitbox h;
    private boolean inside;

    public Button(int x, int y, int width, int height, MouseButtons trigger, Consumer<MouseEvent> onClick) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        h = new Hitbox(new Point(x, y), new Point(x + width, y + height));

        mouseListener.addEvent(trigger, e -> {
            if (h.isInside(mouseListener.getMousePos())) {
                onClick.accept(e);
                return true;
            }
            return false;
        });
    }

    @Override
    public void init() {
        //
    }

    @Override
    public void logicLoop() {
        inside = h.isInside(mouseListener.getMousePos());
    }

    @Override
    public void renderLoop() {
        if (inside) g.setColor(Color.black);
        else g.setColor(Color.darkGray);
        g.fillRect(x, y, width, height);
    }
}
