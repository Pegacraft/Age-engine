package game.test;

import engine.Game;
import engine.Object;
import engine.listeners.Keyboard;
import engine.loops.Loop;
import engine.mechanics.Hitbox;
import engine.rendering.Animation;
import engine.rendering.Graphics;
import engine.rendering.Image;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player2 extends Object {
    private final int width = 20, height = 20, speed = 5;
    private final Keyboard keyListener = Game.scenes.get("Game").keyListener;
    private final ArrayList<Hitbox> hitList = new ArrayList<>();
    private final BufferedImage img = Image.load("blush.png");
    private int x = 100, y = 100;
    public final Hitbox h = new Hitbox(
            new Point(x, y),
            new Point(x + width, y + height)
    );

    @Override
    public void init() {
        hitList.add(new Hitbox(new Point(0, 0), new Point(200, 50)));
        hitList.add(new Hitbox(new Point(200, 200), new Point(400, 400), new Point(200, 300)));
        Animation.createAnimation(Image.load("testSheet.png"), 20, 4, 20, null, "Idle");
        Game.scenes.get("Game").keyListener.addListener(KeyEvent.VK_SPACE, e -> Animation.play("Idle", x, y), false);
    }

    @Override
    public void logicLoop() {
        for (double velo = ((keyListener.isHeld('W') ? 0 : 1) - (keyListener.isHeld('S') ? 0 : 1)) * speed;
             Math.abs(velo) >= 1; velo *= 0.9) {
            h.move(x, (int) (y + velo));
            boolean hit = false;
            for (Hitbox e : hitList) if (h.isInside(e)) hit = true;
            if (!hit) {
                y += velo;
                break;
            }
        }

        for (double velo = ((keyListener.isHeld('A') ? 0 : 1) - (keyListener.isHeld('D') ? 0 : 1)) * speed;
             Math.abs(velo) > 1; velo *= 0.9) {
            h.move((int) (x + velo), y);
            boolean hit = false;
            for (Hitbox e : hitList) if (h.isInside(e)) hit = true;
            if (!hit) {
                x += velo;
                break;
            }
        }
        h.move(x, y);
    }

    @Override
    public void renderLoop() {
        Animation.updatePos("Idle", x, y);
        Graphics.g.setColor(Color.CYAN);
        Graphics.g.drawImage(img, x, y, width, height, null);
        Graphics.g.draw(h.shape);
        Graphics.g.drawString(String.format("Time per frame in ms: %.3f", Loop.frameTime / 1E6), 0, 100);
        hitList.forEach(e -> Graphics.g.draw(e.shape));
    }
}
