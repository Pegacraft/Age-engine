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

    private final ArrayList<Tile> Selector = new ArrayList<>();
    private final ArrayList<Sprite> sprites = new ArrayList<>();
    private final Hitbox workArea = new Hitbox(new Point(0, 0), new Point(1100, 720));
    private final TextBox scaleBox = new TextBox(1190, 460, 30, 20, this);
    public double scale = 1;

    @Override
    public void init() {
        Selector.add(new Tile(1100, 0, workArea));
        Selector.add(new Tile(1100, 90, workArea));
        Selector.add(new Tile(1100, 180, workArea));
        Selector.add(new Tile(1100, 270, workArea));
        Selector.add(new Tile(1100, 360, workArea));
        Selector.add(new Tile(1190, 0, workArea));
        Selector.add(new Tile(1190, 90, workArea));
        Selector.add(new Tile(1190, 180, workArea));
        Selector.add(new Tile(1190, 270, workArea));
        Selector.add(new Tile(1190, 360, workArea));

        this.addObject(scaleBox);

        mouseListener.addEvent(MouseButtons.LEFT_DOWN, e -> {
            if (workArea.isInside(mouseListener.getMousePos())) {
                Selector.forEach(p -> {
                    if (p.selected && p.importPath != null) {
                        String s = p.importPath;
                        sprites.add(new Sprite(mouseListener.getMousePos().x, mouseListener.getMousePos().y, scale, s, this));
                    }
                });
            }
        }, false);
    }

    @Override
    public void logicLoop() {
        try {
            scale = Double.parseDouble(scaleBox.text);
        } catch (NumberFormatException ignore) {
        }
        sprites.forEach(e -> {
            if (e.delete) mouseListener.deleteEvent(MouseButtons.RIGHT_DOWN, e.onClickEvent);
        });
        sprites.removeIf(sprite -> sprite.delete);
        Selector.forEach(Tile::logicLoop);
        sprites.forEach(Sprite::logicLoop);
    }

    @Override
    public void renderLoop() {
        try {
            sprites.forEach(Sprite::renderLoop);
            Selector.forEach(Tile::renderLoop);
        } catch (ConcurrentModificationException ignored) {
        }
        g.setColor(Color.BLACK);
        g.drawString("Scale:", 1150, 475);
        g.draw(workArea.shape);
    }
}
