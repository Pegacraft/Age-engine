package game.test;

import engine.Game;
import engine.Object;
import engine.listeners.Mouse;
import engine.listeners.MouseButtons;
import engine.mechanics.Hitbox;
import engine.rendering.Graphics;
import engine.sound.Sound;

import java.awt.*;

public class Button extends Object {
    private final int x = 500, y = 500, width = 100, height = 50;
    private final Mouse mouseListener = Game.scenes.get("Game").mouseListener;
    private final Hitbox h = new Hitbox(new Point(x, y), new Point(x + width, y + height));
    private final Sound s = new Sound("escape.wav").setVolume(-20.0f);
    boolean playing = false;

    @Override
    public void init() {
        mouseListener.addEvent(MouseButtons.LEFT_DOWN, e -> {
            if (h.isInside(mouseListener.getMousePos())) {
                if (!playing) {
                    s.playSound();
                    playing = true;
                } else {
                    s.stopSound();
                    playing = false;
                }
            }
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
