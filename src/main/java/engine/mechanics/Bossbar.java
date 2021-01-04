package engine.mechanics;

import engine.Entity;
import engine.rendering.Image;

import java.awt.*;
import java.awt.image.BufferedImage;

import static engine.rendering.Graphics.g;

public class Bossbar extends Entity {
    private int width, height, maxValue = 100, currentValue = 50;
    private BufferedImage backgroundImage, barImage, overlayImage;
    private Color backgroundColor = Color.GRAY, barColor = Color.green;

    public Bossbar(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void init() {

    }

    @Override
    public void logicLoop() {

    }

    @Override
    public void renderLoop() {
        if (backgroundImage == null) {
            g.setColor(backgroundColor);
            g.fillRect(x, y, width, height);
            g.setColor(barColor);
        } else {
            g.drawImage(backgroundImage, x, y, width, height, null);
        }
        if (barImage == null)
            g.fillRect(x, y, (int) (width * ((double) currentValue / (double) maxValue)), height);
        else {
            BufferedImage croped = barImage.getSubimage(0, 0, (int) (barImage.getWidth() * ((double) currentValue / (double) maxValue)), barImage.getHeight());
            g.drawImage(croped, x, y, (int) (width * ((double) currentValue / (double) maxValue)), height, null);
        }
    }

    public Bossbar setX(int x) {
        this.x = x;
        return this;
    }

    public Bossbar setY(int y) {
        this.y = y;
        return this;
    }

    public Bossbar setWidth(int width) {
        this.width = width;
        return this;
    }

    public Bossbar setHeight(int height) {
        this.height = height;
        return this;
    }

    public Bossbar setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public Bossbar setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
        return this;
    }

    public Bossbar setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }


    public Bossbar setBarColor(Color barColor) {
        this.barColor = barColor;
        return this;
    }

    public Bossbar setBackgroundImage(String path) {
        backgroundImage = Image.load(path);
        return this;
    }

    public Bossbar setBarImage(String path) {
        barImage = Image.load(path);
        return this;
    }
}
