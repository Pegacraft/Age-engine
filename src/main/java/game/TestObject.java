package game;

import engine.Entity;
import engine.Scene;
import engine.rendering.Graphics;

public class TestObject implements Entity {
    public int i = 0;

    public TestObject(Scene scene) {
    }

    @Override
    public void init() {
        // TBD
    }

    @Override
    public void logicLoop() {
        i++;
    }

    @Override
    public void renderLoop() {
        Graphics.g.fillRect(0, 200, i, 520);
    }
}
