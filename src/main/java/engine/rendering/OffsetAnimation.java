package engine.rendering;

import engine.Entity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import static engine.rendering.Graphics.g;

public class OffsetAnimation implements Entity {

    int x, y;
    BufferedImage[] images;
    ArrayList<Integer> offsetX = new ArrayList<>();
    ArrayList<Integer> offsetY = new ArrayList<>();


    public OffsetAnimation(int x, int y, BufferedImage... images) {
        this.x = x;
        this.y = y;
        this.images = images;
        for (int i = 0; i < images.length; i++) {
            offsetX.add(0);
            offsetY.add(0);
        }
    }

    public OffsetAnimation offset(int ImageID, int xOffset, int yOffset){

        offsetX.set(ImageID, xOffset);
        offsetY.set(ImageID, yOffset);
        return this;
    }

    @Override
    public void init() {

    }

    @Override
    public void logicLoop() {

    }

    @Override
    public void renderLoop() {
        for (int i = 0; i < images.length; i++) {
            g.drawImage(images[i], x + offsetX.get(i), y + offsetY.get(i), null);
        }
    }
}
