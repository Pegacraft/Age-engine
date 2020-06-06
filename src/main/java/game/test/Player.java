package game.test;

import engine.Game;
import engine.Object;
import engine.listeners.Keyboard;
import engine.listeners.Mouse;
import engine.listeners.MouseButtons;
import engine.mechanics.Hitbox;
import engine.rendering.Graphics;

import java.awt.*;

public class Player extends Object {

    public final int width = 50, height = 50;
    private final Keyboard keyListener = Game.scenes.get("Game").keyListener;
    private final Mouse mouseListener = Game.scenes.get("Game").mouseListener;
    public double accel = 1;
    private Hitbox h, c;
    private double xspeed, yspeed, x = 400, y = 200;

    @Override
    public void init() {
        h = new Hitbox(
                new Point((int) (x + width), (int) (y)),
                new Point((int) (x + width / 2), (int) y),
                new Point((int) (x), (int) (y + height / 2)),
                new Point((int) (x + width / 2), (int) (y + height)),
                new Point((int) (x + width), (int) (y + height / 2))
        );
        c = new Hitbox(
                new Point(100, 100),
                new Point(100, 200),
                new Point(200, 300),
                new Point(300, 200),
                new Point(200, 100)
        );
        mouseListener.addEvent(MouseButtons.LEFT_DOWN, e -> {
            this.x = mouseListener.getMousePos().x;
            this.y = mouseListener.getMousePos().y;
        }, false);
    }

    @Override
    public void logicLoop() {
        xspeed = xspeed * 0.9 + ((keyListener.isHeld('A') ? 0 : 1) - (keyListener.isHeld('D') ? 0 : 1) * accel);
        yspeed = yspeed * 0.9 + ((keyListener.isHeld('W') ? 0 : 1) - (keyListener.isHeld('S') ? 0 : 1)) * accel;
        boolean wasInside = false;
        int bounce = 128;
        do {
            h.move((int) (x + xspeed), (int) (y + yspeed));
            xspeed *= 0.9;
            yspeed *= 0.9;
            if (Math.abs(xspeed) <= 0.01 && Math.abs(yspeed) <= 0.01) {
                if (h.isInside(c)) {
                    if (wasInside) bounce *= 2;
                    else {
                        if (xspeed == 0 && yspeed == 0) xspeed = Math.random();
                        xspeed *= -1;
                        yspeed *= -1;
                        wasInside = true;
                    }
                    xspeed *= bounce;
                    yspeed *= bounce;
                } else break;
            }
        } while (h.isInside(c));
        x += xspeed;
        y += yspeed;
        h.move((int) x, (int) y);
    }

    @Override
    public void renderLoop() {
        if (h.isInside(c)) Graphics.g.setColor(Color.CYAN);
        else Graphics.g.setColor(Color.RED);

        Graphics.g.draw(c.shape);
        Graphics.g.draw(h.shape);
        Graphics.g.drawString(String.format("pos: %.1f; %.1f", x, y), 0, 13);
    }
}
