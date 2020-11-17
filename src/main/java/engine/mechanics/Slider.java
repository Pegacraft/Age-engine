package engine.mechanics;

import engine.Entity;
import engine.Scene;
import engine.listeners.Mouse;

import static engine.rendering.Graphics.g;

import java.awt.*;

public class Slider extends Entity {

    private Color sliderColor = Color.LIGHT_GRAY;
    private int barAlpha = 255;
    private Color barColor = Color.DARK_GRAY;
    private int barLength = 100;
    private int barThickness = 10;
    private int sliderWidth = 10;
    private int sliderHeight = 20;
    private double sliderPosition = 0;
    private boolean slide = false;

    private final int x, y;
    private int sliderX;
    private int sliderY;
    private final Mouse mouseListener;

    private Hitbox slider;

    public Slider(int x, int y, Scene scene) {
        this.x = x;
        this.y = y;
        this.sliderX = x;
        this.sliderY = y - (sliderHeight / 2) + (barThickness / 2);
        this.mouseListener = scene.mouseListener;
    }

    @Override
    public void init() {
        slider = new Hitbox(new Point(sliderX, sliderY), new Point(sliderX + sliderWidth, sliderY + sliderHeight));
    }

    @Override
    public void logicLoop() {
        if (!slide)
            slide = mouseListener.isHeld(1) && slider.isInside(mouseListener.getMousePos());
        else if (mouseListener.isHeld(1)) {
            sliderX = (mouseListener.getMousePos().x - sliderWidth / 2);
        } else
            slide = false;

        if (sliderX < x - (sliderWidth / 2))
            sliderX = x - (sliderWidth / 2);
        if (sliderX > x + barLength - (sliderWidth / 2))
            sliderX = x + barLength - (sliderWidth / 2);
        slider.move(sliderX, sliderY);
    }

    @Override
    public void renderLoop() {
        barColor = new Color(barColor.getRed(), barColor.getGreen(), barColor.getBlue(), barAlpha);
        g.setColor(barColor);
        g.fillRect(x, y, barLength, barThickness);
        g.setColor(sliderColor);
        g.fillRect(sliderX, sliderY, sliderWidth, sliderHeight);

    }

    private void calcSliderPos() {
        sliderPosition = (double) (sliderX - x + (sliderWidth / 2)) / barLength;
    }

    public double getSliderPos() {
        calcSliderPos();
        return sliderPosition;
    }

    public Slider barAlpha(int barAlpha) {
        this.barAlpha = barAlpha;
        return this;
    }

    public Slider sliderColor(Color sliderColor) {
        this.sliderColor = sliderColor;
        return this;
    }

    public Slider barLength(int barLength) {
        this.barLength = barLength;
        return this;
    }

    public Slider barColor(Color barColor) {
        this.barColor = barColor;
        return this;
    }

    public Slider barThickness(int barThickness) {
        sliderY = y - (sliderHeight / 2) + (barThickness / 2);
        this.barThickness = barThickness;
        return this;
    }

    public Slider sliderHeight(int sliderHeight) {
        sliderY = y - (sliderHeight / 2) + (barThickness / 2);
        this.sliderHeight = sliderHeight;
        return this;
    }

    public Slider sliderWidth(int sliderWidth) {
        this.sliderWidth = sliderWidth;
        return this;
    }

    public Slider sliderPosition(double sliderPosition) {
        this.sliderX = (int) (x + (sliderPosition * barLength) - (sliderWidth / 2));
        this.sliderPosition = sliderPosition;
        return this;
    }
}
