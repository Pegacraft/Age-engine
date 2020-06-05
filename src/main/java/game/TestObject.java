package game;

import engine.Object;
import engine.Scene;
import engine.mechanics.Hitbox;
import engine.rendering.Graphics;

import java.awt.*;

public class TestObject extends Object {
    public int i = 0;
    private Scene scene;
    Hitbox h = new Hitbox(new Point(100, 100), new Point(0, 0));

    public TestObject(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void init() {
        System.out.println("Got initted");
    }

    @Override
    public void logicLoop() {
        i++;
        if (h.isInside(scene.mouseListener.getMousePos()))
            System.out.println("test");
    }

    @Override
    public void renderLoop() {
        Graphics.g.fillRect(0, 200, i, 520);
    }
}
