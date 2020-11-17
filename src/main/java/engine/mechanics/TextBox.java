package engine.mechanics;

import engine.Entity;
import engine.Scene;
import engine.listeners.MouseButtons;

import java.awt.*;

import static engine.rendering.Graphics.g;

public class TextBox extends Entity {
    private final Scene scene;
    private final Font font;
    private final Hitbox h;
    private TextField textField;
    private String displayText = "";
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean selected = false;
    private Color fontColor = Color.BLACK;
    private Color borderColor = Color.BLACK;
    private String textType = "JhengHei UI";
    private int fontSize = 12;
    private int maxValue = 100;
    private int caretPos;
    private int counter;
    private boolean matching;
    private String matcher = ".*";

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
            selected = h.isInside(scene.mouseListener.getMousePos());
            if (selected) {
                textField.setText(displayText);
                caretPos = 0;
                textField.select(displayText.length(), displayText.length());
            }
            return false;
        });
    }

    public void logicLoop() {
        matching = displayText.matches(matcher);
        if (counter++ >= 30) counter = 0;
    }

    public void renderLoop() {
        if (selected) {
            textField.requestFocus();
            while (!textField.isFocusOwner()) Thread.yield();
            caretPos = textField.getCaretPosition();
            displayText = textField.getText();
            if (displayText.length() > maxValue) {
                displayText = displayText.substring(0, maxValue);
                textField.setText(displayText);
                textField.setCaretPosition(displayText.length());
                caretPos = textField.getCaretPosition();
            }
        }
        try {
            String text1 = displayText.substring(0, caretPos);
            String text2 = displayText.substring(caretPos);
            g.setFont(font);
            int width1 = g.getFontMetrics().stringWidth(text1);
            int width2 = g.getFontMetrics().stringWidth(text2);
            int fontHeight = g.getFontMetrics().getHeight();
            int drawStrX = (int) (x + width / 2.0 - (width1 + width2) / 2.0);
            int drawStrY = (int) (y + height / 2.0 + fontHeight / 4.0);
            if (selected) {
                g.setColor(Color.cyan);
                g.fillRect(drawStrX + g.getFontMetrics().stringWidth(displayText.substring(0, textField.getSelectionStart())), y,
                        g.getFontMetrics().stringWidth(textField.getSelectedText()), height);
            }
            if (matching) {
                if (selected) g.setColor(fontColor);
                else g.setColor(fontColor.brighter());
            } else g.setColor(Color.red);
            g.drawString(text1, drawStrX, drawStrY);
            if (selected && counter < 15)
                g.drawLine(drawStrX + width1, drawStrY, drawStrX + width1, drawStrY - fontHeight);
            g.drawString(text2, drawStrX + width1, drawStrY);
            g.setColor(borderColor);
            g.draw(h.getShape());
        } catch (StringIndexOutOfBoundsException ignore) {
        }
    }

    /**
     * Sets the x position of the text box.
     *
     * @param x The x position it should be set at.
     */
    public TextBox setX(int x) {
        this.x = x;
        return this;
    }

    /**
     * Sets the y position of the text box.
     *
     * @param y The y position it should be set at.
     */
    public TextBox setY(int y) {
        this.y = y;
        return this;
    }

    /**
     * Sets the width of the text box.
     *
     * @param width The width it should be set at.
     */
    public TextBox setWidth(int width) {
        this.width = width;
        return this;
    }

    /**
     * Sets the height of the text box.
     *
     * @param height The height it should be set at.
     */
    public TextBox setHeight(int height) {
        this.height = height;
        return this;
    }

    /**
     * Sets the color of the text in the box.
     *
     * @param fontColor The color it should be set to.
     */
    public TextBox setFontColor(Color fontColor) {
        this.fontColor = fontColor;
        return this;
    }

    /**
     * Sets the border color of the text box.
     *
     * @param borderColor The color it should be set to.
     */
    public TextBox setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    /**
     * Sets the font you want the text to be shown in.
     *
     * @param textType The font you want to be set.
     */
    public TextBox setTextType(String textType) {
        this.textType = textType;
        return this;
    }

    /**
     * Sets the size of the text.
     *
     * @param fontSize The size that should be used.
     */
    public TextBox setFontSize(int fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    /**
     * Sets the maximum characters in the text box.
     *
     * @param maxValue The maximum value that should be used.
     */
    public TextBox setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    /**
     * @return Returns the text in the text box.
     */
    public String getText() {
        return displayText;
    }

    /**
     * Sets the text of the text box
     *
     * @param text The the text it should be set to.
     */
    public TextBox setText(String text) {
        this.displayText = text;
        scene.display.getTextField().setText(text);
        return this;
    }

    /**
     * Applies an matcher to prohibit certain characters. If a text box contains illegal characters, the text
     * will be highlighted in red.
     *
     * @param regex The regex that you want to apply.
     */
    public TextBox setMatcher(String regex) {
        this.matcher = regex;
        return this;
    }
}
