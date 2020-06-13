package engine.editor;

import engine.Scene;
import engine.editor.menu.Sprite;
import engine.listeners.MouseButtons;
import engine.mechanics.Hitbox;
import engine.mechanics.TextBox;

import java.awt.*;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

import static engine.rendering.Graphics.g;

public class EditScene extends Scene {

    private final ArrayList<Sprite> sprites = new ArrayList<>();
    private final Hitbox workArea = new Hitbox(new Point(0, 0), new Point(1100, 720));
    private final TextBox scaleBox = new TextBox(1190, 460, 30, 20, this);
    public double scale = 1;
    public Tile selection;

    @Override
    public void init() {
        for (int i = 0; i < 10; i++)
            addObject(new Tile(1100 + (i / 5) * 90, (i % 5) * 90));
        addObject(scaleBox);
        mouseListener.addEvent(MouseButtons.LEFT_DOWN, e -> {
            if (workArea.isInside(mouseListener.getMousePos()) && selection != null && selection.importPath != null)
                sprites.add(new Sprite(
                        mouseListener.getMousePos().x, mouseListener.getMousePos().y,
                        scale, selection.importPath, this));
        }, false);
    }

    @Override
    public void logicLoop() {
        try {
            scale = Double.parseDouble(scaleBox.text);
        } catch (NumberFormatException ignore) {
        }
        try {
            sprites.forEach(e -> {
                if (e.delete) mouseListener.deleteEvent(MouseButtons.RIGHT_DOWN, e.onClickEvent);
            });
            sprites.removeIf(sprite -> sprite.delete);
        } catch (ConcurrentModificationException ignore) {
        }
        sprites.forEach(Sprite::logicLoop);
    }

    @Override
    public void renderLoop() {
        try {
            sprites.forEach(Sprite::renderLoop);
            if (selection != null)
                selection.drawSelectionHUD();
        } catch (ConcurrentModificationException ignored) {
        }
        g.setColor(Color.BLACK);
        g.drawString("Scale:", 1150, 475);
        g.draw(workArea.shape);
    }
}
