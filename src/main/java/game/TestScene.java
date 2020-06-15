package game;

import engine.Entity;
import engine.Scene;
import engine.loops.Loop;
import engine.rendering.Graphics;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TestScene extends Scene {

    @Override
    public void init() {
        display.keyListener.addListener(KeyEvent.VK_SPACE, this::reset, false);
        display.keyListener.addListener(KeyEvent.VK_ESCAPE, e -> {
            System.exit(0);
            return false;
        });
        this.addObject(new TestObject(this));
    }

    @Override
    public void logicLoop() {
        for (int j = 0; j++ <= 1E6; ) Math.sin(4); // benchmark to increase the frameTimes
    }

    public void reset(KeyEvent e) {
        Entity o = this.getObject(0);
        if (o instanceof TestObject) {
            TestObject t = (TestObject) o;
            t.i = 0;
        }
    }

    @Override
    public void renderLoop() {
        Graphics.g.setColor(Color.BLACK);
        Graphics.g.drawString(String.format("Time per frame in ms: %.3f", Loop.frameTime / 1E6), 0, 100);
    }
}
