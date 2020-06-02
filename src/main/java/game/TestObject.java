package game;

import engine.Object;
import engine.rendering.Graphics;

public class TestObject extends Object {
    public int i = 0;

    @Override
    public void init() {
        System.out.println("Got initted");
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
