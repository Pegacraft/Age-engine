package engine.editor.menu;

import engine.Entity;
import engine.Game;
import engine.listeners.Mouse;
import engine.listeners.MouseButtons;
import engine.mechanics.Hitbox;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.EnumMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static engine.rendering.Graphics.g;

public class Button implements Entity {
    private final Mouse mouseListener = Game.getScene("EditScene").mouseListener;
    private final Hitbox h;
    private final Map<MouseButtons, Predicate<MouseEvent>> events = new EnumMap<>(MouseButtons.class);
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private boolean inside;
    private Color color = Color.black;
    private Color hoverColor = Color.darkGray;
    private String text = "";
    private Font font;
    private Color textColor = Color.black;
    private int fontSize = 13;
    private String fontFace = "Comic Sans MS";

    public Button(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        h = new Hitbox(new Point(x, y), new Point(x + width, y + height));
        updateFont();
    }

    public Button addEvent(MouseButtons trigger, Consumer<MouseEvent> onClick) {
        mouseListener.deleteEvent(trigger, events.get(trigger));
        events.put(trigger, e -> {
            if (h.isInside(mouseListener.getMousePos())) {
                onClick.accept(e);
                return true;
            }
            return false;
        });
        mouseListener.addEvent(trigger, events.get(trigger));
        return this;
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
        if (inside) g.setColor(hoverColor);
        else g.setColor(color);
        g.fill(h.getShape());
        if (text != null) {
            g.setFont(font);
            int fontWidth = g.getFontMetrics().stringWidth(text);
            int fontHeight = g.getFontMetrics().getHeight();

            int drawStrX = (int) (x + width / 2.0 - fontWidth / 2.0);
            int drawStrY = (int) (y + height / 2.0 + fontHeight / 4.0);
            g.setColor(textColor);
            g.drawString(text, drawStrX, drawStrY);
        }
    }

    public Button setColor(Color color) {
        this.color = color;
        return this;
    }

    public Button setHoverColor(Color hoverColor) {
        this.hoverColor = hoverColor;
        return this;
    }

    public Button setText(String text) {
        this.text = text;
        return this;
    }

    public Button setFont(String font) {
        this.fontFace = font;
        updateFont();
        return this;
    }

    public Button setFontSize(int size) {
        this.fontSize = size;
        updateFont();
        return this;
    }

    public Button setTextColor(Color color) {
        this.textColor = color;
        updateFont();
        return this;
    }

    private void updateFont() {
        this.font = new Font(fontFace, Font.PLAIN, fontSize);
    }
}
