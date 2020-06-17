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
    private final Mouse mouseListener = Game.getScene("EditScene").mouseListener;
    private final Hitbox h;
    private boolean inside;

    public Button() {
        h = new Hitbox(new Point(0, 0), new Point(0, 0));
    }

    public Button(int x, int y, int width, int height, MouseButtons trigger, Consumer<MouseEvent> onClick) {
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
        if (inside) g.setColor(Color.darkGray);
        else g.setColor(Color.black);
        g.fill(h.getShape());
    }
}
