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

import static engine.rendering.Graphics.g;
import static java.lang.Math.toRadians;

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
    private int i = 0;

    @Override
    public void init() {
        hitList.add(new Hitbox(new Point(50, 0), new Point(200, 50)));
        hitList.add(new Hitbox(new Point(200, 200), new Point(400, 400), new Point(200, 300)));
        hitList.get(0).rotate(1);
        Animation.createAnimation(Image.load("testSheet.png"), 3, 4, 20, null, "Idle");
        keyListener.addListener(KeyEvent.VK_SPACE, e -> Animation.play("Idle", x, y), false);
        keyListener.addListener(KeyEvent.VK_SPACE, e -> y += 10, false);
    }

    @Override
    public void logicLoop() {
        hitList.get(1).rotate(toRadians(i++));
        boolean inside = false;
        for (Hitbox e : hitList) if (h.isInside(e)) inside = true;
        if (!inside) {
            for (double velo = ((keyListener.isHeld('W') ? 0 : 1) - (keyListener.isHeld('S') ? 0 : 1)) * speed;
                 Math.abs(velo) >= 1; velo *= 0.9) {
                h.move(x, (int) (y + velo));
                inside = false;
                for (Hitbox e : hitList) if (h.isInside(e)) inside = true;
                if (!inside) {
                    y += velo;
                    break;
                }
            }

            for (double velo = ((keyListener.isHeld('A') ? 0 : 1) - (keyListener.isHeld('D') ? 0 : 1)) * speed;
                 Math.abs(velo) >= 1; velo *= 0.9) {
                h.move((int) (x + velo), y);
                inside = false;
                for (Hitbox e : hitList) if (h.isInside(e)) inside = true;
                if (!inside) {
                    x += velo;
                    break;
                }
            }
        } else while (inside) {
            y++;
            h.move(x, y);
            inside = false;
            for (Hitbox e : hitList) if (h.isInside(e)) inside = true;
        }
        h.move(x, y);
        Graphics.moveCam(x, y);
    }

    @Override
    public void renderLoop() {
        Animation.updatePos("Idle", x, y);
        if (hitList.get(0).isInside(Game.scenes.get("Game").mouseListener.getMousePos()))
            g.setColor(Color.CYAN);
        else
            g.setColor(Color.blue);
        g.drawImage(img, x, y, width, height, null);
        g.draw(h.shape);
        g.drawString(String.format("Time per frame in ms: %.3f", Loop.frameTime / 1E6), -Graphics.getCamPos().x, 100 - Graphics.getCamPos().y);
        hitList.forEach(e -> g.draw(e.shape));
    }
}
