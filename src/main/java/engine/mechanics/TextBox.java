package engine.mechanics;

import engine.Entity;
import engine.Scene;
import engine.listeners.MouseButtons;

import java.awt.*;

import static engine.rendering.Graphics.g;

public class TextBox implements Entity {
    private final Scene scene;
    private final Font font;
    private final Hitbox h;
    private TextField textField;
    private String displayText = "";
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean clicked = false;
    private Color fontColor = Color.BLACK;
    private Color borderColor = Color.BLACK;
    private String textType = "JhengHei UI";
    private int fontSize = 12;
    private int maxValue = 10;

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

    public void init() {
        textField = scene.display.getTextField();
        scene.mouseListener.addEvent(MouseButtons.LEFT_DOWN, e -> {
            if (h.isInside(scene.mouseListener.getMousePos())) {
                clicked = h.isInside(scene.mouseListener.getMousePos());
                scene.display.getTextField().setText(displayText.replace("|", ""));
            } else {
                clicked = false;
                displayText = displayText.replace("|", "");
            }
        }, false);
    }

    public void logicLoop() {
        // textBoxes don't need logic loops
    }

    public void renderLoop() {
        if (clicked) {
            textField.requestFocus();
            int caretPos = textField.getCaretPosition();
            displayText = textField.getText();
            if (displayText.length() > maxValue) {
                textField.setText(displayText.substring(0, maxValue));
                textField.setCaretPosition(maxValue);
            }
            try {
                displayText = displayText.substring(0, caretPos) + "|" + displayText.substring(caretPos);
            } catch (StringIndexOutOfBoundsException ignore) { //
            }
        }
        g.setFont(font);
        int fontWidth = g.getFontMetrics().stringWidth(displayText);
        int fontHeight = g.getFontMetrics().getHeight();

        int drawStrX = (int) (x + width / 2.0 - fontWidth / 2.0);
        int drawStrY = (int) (y + height / 2.0 + fontHeight / 4.0);
        g.setColor(fontColor);
        g.drawString(displayText, drawStrX, drawStrY);
        g.setColor(borderColor);
        g.draw(h.getShape());
    }

    public TextBox setX(int x) {
        this.x = x;
        return this;
    }

    public TextBox setY(int y) {
        this.y = y;
        return this;
    }

    public TextBox setWidth(int width) {
        this.width = width;
        return this;
    }

    public TextBox setHeight(int height) {
        this.height = height;
        return this;
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

    public String getText() {
        return displayText.replace("|", "");
    }

    public TextBox setText(String text) {
        this.displayText = text;
        scene.display.getTextField().setText(text);
        return this;
    }
}
