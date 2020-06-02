package game;

import engine.Scene;
import engine.loops.Loop;
import engine.rendering.Graphics;

import java.awt.*;
import java.awt.event.KeyEvent;

public class TestScene extends Scene {
    static int i = 0;

    @Override
    public void init() {
    }

    @Override
    public void logicLoop() {
//        for (int j = 0; j++ <= 1E6; ) Math.sin(4); // benchmark to increase the frameTimes
    }

    public static boolean reset(KeyEvent e) {
        i = 0;
        return false;
    }

    @Override
    public void renderLoop() {
        Graphics.g.setColor(Color.BLACK);
        Graphics.g.drawString(String.format("Time per frame in ms: %.3f", Loop.frameTime / 1E6), 0, 100);
        Graphics.g.fillRect(0, 200, i++, 520);
    }
}
