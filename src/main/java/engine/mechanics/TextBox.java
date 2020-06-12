package engine.mechanics;

import engine.Object;
import engine.Scene;
import engine.listeners.MouseButtons;

import java.awt.*;

import static engine.rendering.Graphics.g;

public class TextBox extends Object {
    public String text = "";
    private final Scene scene;
    private int x, y, width, height;
    private boolean clicked = false;
    private Color fontColor = Color.BLACK;
    private Color borderColor = Color.BLACK;
    private String textType = "JhengHei UI";
    private int fontSize = 12;
    private final Font font;
    private int maxValue = 10;

    Hitbox h;

    /**
     * This is a text box. Use it to create an input field.
     *
     * @param x      x pos of the field
     * @param y      y pos of the field
     * @param width  width of the field
     * @param height height of the field
     * @param scene  scene where the text box should be inserted
     */
    public TextBox(int x, int y, int width, int height, Scene scene) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scene = scene;
        font = new Font(textType, Font.PLAIN, fontSize);
        h = new Hitbox(new Point(x, y), new Point(x + width, y + height));
    }

    @Override
    public void init() {
        scene.mouseListener.addEvent(MouseButtons.LEFT_DOWN, e -> {
            if (h.isInside(scene.mouseListener.getMousePos())) {
                clicked = h.isInside(scene.mouseListener.getMousePos());
                scene.display.textField.setText(text.replace("|", ""));
            } else {
                clicked = false;
                text = text.replace("|", "");
            }
        }, false);
    }

    public void logicLoop() {

    }

    public void renderLoop() {
        if (clicked) {
            scene.display.textField.requestFocus();
            int caretPos = scene.display.textField.getCaretPosition();
            text = scene.display.textField.getText();
            if (text.length() > maxValue) {
                scene.display.textField.setText(text.substring(0, maxValue));
                scene.display.textField.setCaretPosition(maxValue);
            }
            try {
                text = text.substring(0, caretPos) + "|" + text.substring(caretPos);
            } catch (StringIndexOutOfBoundsException ignore) {
            }
        }
        g.setFont(font);
        int fontWidth = g.getFontMetrics().stringWidth(text);
        int fontHeight = g.getFontMetrics().getHeight();

        int drawStrX = (int) (x + width / 2.0 - fontWidth / 2.0);
        int drawStrY = (int) (y + height / 2.0 + fontHeight / 4.0);
        g.setColor(fontColor);
        g.drawString(text, drawStrX, drawStrY);
        g.setColor(borderColor);
        g.draw(h.shape);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public TextBox setFontColor(Color fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    public TextBox setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public TextBox setTextType(String textType) {
        this.textType = textType;
        return this;
    }

    public TextBox setFontSize(int fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public TextBox setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        return this;
    }
}
