package game;

import engine.Scene;
import engine.rendering.Graphics;

import java.awt.*;

public class TestScene extends Scene {
    int i = 0;
    @Override
    public void init() {

    }

    @Override
    public void logicLoop() {

    }

    @Override
    public void renderLoop() {
        Graphics.g.setColor(Color.BLACK);
        Graphics.g.fillRect(0,0,200,200);
    }
}
