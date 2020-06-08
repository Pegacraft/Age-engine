package game.test;

import engine.Game;
import engine.Object;
import engine.listeners.Mouse;
import engine.listeners.MouseButtons;
import engine.mechanics.Hitbox;
import engine.rendering.Graphics;

import java.awt.*;

public class Button extends Object {
    int x = 500, y = 500, width = 100, height = 50;
    Mouse mouseListener = Game.scenes.get("Game").mouseListener;
    Hitbox h = new Hitbox(new Point(x, y), new Point(x + width, y + height));

    @Override
    public void init() {
        mouseListener.addEvent(MouseButtons.LEFT_DOWN, e -> {
            if (h.isInside(mouseListener.getMousePos()))
                System.out.println("test");
        }, false);
    }

    @Override
    public void logicLoop() {

    }

    @Override
    public void renderLoop() {
        Graphics.g.setColor(Color.red);
        Graphics.g.fillRect(x, y, width, height);
    }
}
