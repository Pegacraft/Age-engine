package game.test;

import engine.Game;
import engine.Object;
import engine.mechanics.Hitbox;
import engine.rendering.Graphics;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player extends Object {

    public int x = 300, y = 200, width = 50, height = 50, speed = 5, slow = 0;
    Hitbox h, c;

    @Override
    public void init() {
        h = new Hitbox(new Point(x, y), new Point(x + width, y + height));
        c = new Hitbox(new Point(100, 100), new Point(200, 200));
    }

    @Override
    public void logicLoop() {
        if (Game.scenes.get("Game").keyListener.isHeld(KeyEvent.VK_W)) {
            for (int i = 0; i < speed; i++) {
                h.move(x, y - speed + slow);
                if (c.isInside(h))
                    slow += 1;
                h.move(x, y);
            }
            y = y - speed + slow + 1;
            slow = 0;
        }
        if (Game.scenes.get("Game").keyListener.isHeld(KeyEvent.VK_S)) {
            for (int i = 0; i < speed; i++) {
                h.move(x, y + speed - slow);
                if (c.isInside(h))
                    slow += 1;
                h.move(x, y);
            }
            y = y + speed - slow;
            slow = 0;
        }
        if (Game.scenes.get("Game").keyListener.isHeld(KeyEvent.VK_A)) {
            for (int i = 0; i < speed; i++) {
                h.move(x - speed + slow, y);
                if (c.isInside(h))
                    slow += 1;
                h.move(x, y);
            }
            x = x - speed + slow + 1;
            slow = 0;
        }
        if (Game.scenes.get("Game").keyListener.isHeld(KeyEvent.VK_D)) {
            for (int i = 0; i < speed; i++) {
                h.move(x + speed - slow, y);
                if (c.isInside(h))
                    slow += 1;
                h.move(x, y);
            }
            x = x + speed - slow;
            slow = 0;
        }
        h.move(x, y);
    }

    @Override
    public void renderLoop() {
        Graphics.g.setColor(Color.CYAN);
        Graphics.g.fillRect(x, y, width, height);
        h.show();
        c.show();
    }
}
