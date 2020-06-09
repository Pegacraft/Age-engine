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
    public final double acceleration = 3.5;
    private final Keyboard keyListener = Game.scenes.get("Game").keyListener;
    private final Mouse mouseListener = Game.scenes.get("Game").mouseListener;
    private Hitbox h, c;
    private double xSpeed, ySpeed, x = 400, y = 200;

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
        xSpeed = xSpeed * 0.9 + ((keyListener.isHeld('A') ? 0 : 1) - (keyListener.isHeld('D') ? 0 : 1)) * acceleration;
        ySpeed = ySpeed * 0.9 + ((keyListener.isHeld('W') ? 0 : 1) - (keyListener.isHeld('S') ? 0 : 1)) * acceleration;
        boolean wasInside = false;
        double bounce = acceleration;
        do {
            h.move((int) (x + xSpeed), (int) (y + ySpeed));
            xSpeed *= 0.9;
            ySpeed *= 0.9;
            if (Math.abs(xSpeed) <= 0.01 && Math.abs(ySpeed) <= 0.01) {
                if (h.isInside(c)) {
                    if (wasInside) bounce *= acceleration + 1;
                    else {
                        if (xSpeed == 0 && ySpeed == 0) xSpeed = Math.random();
                        xSpeed *= -1;
                        ySpeed *= -1;
                        wasInside = true;
                    }
                    xSpeed *= bounce;
                    ySpeed *= bounce;
                } else break;
            }
        } while (h.isInside(c));
        x += xSpeed;
        y += ySpeed;
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
