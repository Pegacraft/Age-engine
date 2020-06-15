package engine.editor;

import engine.Scene;
import engine.editor.menu.Sprite;
import engine.listeners.MouseButtons;
import engine.mechanics.Grid;
import engine.mechanics.Hitbox;
import engine.mechanics.TextBox;

import java.awt.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static engine.rendering.Graphics.g;

public class EditScene extends Scene {

    private final CopyOnWriteArrayList<Sprite> sprites = new CopyOnWriteArrayList<>();
    private final Hitbox workArea = new Hitbox(new Point(0, 0), new Point(1100, 720));
    private final TextBox scaleBox = new TextBox(1190, 460, 30, 20, this);
    private final TextBox gridWidth = new TextBox(1190, 460, 30, 20, this);
    private final TextBox gridHeight = new TextBox(1190, 460, 30, 20, this);
    private final TextBox gridRow = new TextBox(1190, 460, 30, 20, this);
    private final TextBox gridColumn = new TextBox(1190, 460, 30, 20, this);
    private final Grid grid = new Grid(0, 0, 1100, 720, 10, 10);
    private double scale = 1;
    private Tile selection;

    @Override
    public void init() {
        for (int i = 0; i < 10; i++)
            addObject(new Tile(1100 + (i / 5) * 90, (i % 5) * 90));
        addObject(scaleBox);
        mouseListener.addEvent(MouseButtons.LEFT_DOWN, e -> {
            Point p = grid.toGrid(mouseListener.getMousePos());
            if (workArea.isInside(mouseListener.getMousePos()) && selection != null && selection.importPath != null)
                sprites.add(new Sprite(
                        p.x, p.y,
                        scale, selection.importPath, this));
        }, false);
    }

    @Override
    public void logicLoop() {
        try {
            scale = Double.parseDouble(scaleBox.getText());
        } catch (NumberFormatException ignore) { //
        }
        sprites.forEach(e -> {
            if (e.delete) mouseListener.deleteEvent(MouseButtons.RIGHT_DOWN, e.onClickEvent);
        });
        sprites.removeIf(sprite -> sprite.delete);
        sprites.forEach(Sprite::logicLoop);
    }

    @Override
    public void renderLoop() {
        sprites.forEach(Sprite::renderLoop);
        if (selection != null)
            selection.drawSelectionHUD();
        g.setColor(Color.BLACK);
        g.drawString("Scale:", 1150, 475);
        g.draw(workArea.getShape());
        grid.show(Color.DARK_GRAY);
    }

    public void setSelection(Tile selection) {
        this.selection = selection;
    }
}
