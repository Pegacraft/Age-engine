package game.test;

import engine.Game;
import engine.Object;
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
    private int x = 100, y = 100, width = 20, height = 20, speed = 5, prc = 1;
    BufferedImage img = Image.load("blush.png");
    public Hitbox h = new Hitbox(
            new Point(x, y),
            new Point(x + width, y + height)
    );

    private ArrayList<Hitbox> hitList = new ArrayList<>();

    @Override
    public void init() {
        hitList.add(new Hitbox(new Point(0, 0), new Point(200, 50)));
        hitList.add(new Hitbox(new Point(200, 200), new Point(400, 400), new Point(200, 300)));
        Animation.createAnimation(Image.load("testSheet.png"), 20, 4, 20, null, "Idle");
        Game.scenes.get("Game").keyListener.addListener(KeyEvent.VK_SPACE, e -> {
            Animation.play("Idle", x, y);
        }, false);
    }

    @Override
    public void logicLoop() {
        if (Game.scenes.get("Game").keyListener.isHeld('W')) {
            prc = 0;
            for (int i = 0; i < speed; i++) {
                hitList.forEach(e -> {
                    h.move(x, y - speed + prc);
                    if (h.isInside(e))
                        prc += 1;
                });
            }
            y = y - speed + prc;
        }
        if (Game.scenes.get("Game").keyListener.isHeld('A')) {
            prc = 0;
            for (int i = 0; i < speed; i++) {
                hitList.forEach(e -> {
                    h.move(x - speed + prc, y);
                    if (h.isInside(e))
                        prc += 1;
                });
            }
            x = x - speed + prc;
        }
        if (Game.scenes.get("Game").keyListener.isHeld('S')) {
            prc = 0;
            for (int i = 0; i < speed; i++) {
                hitList.forEach(e -> {
                    h.move(x, y + speed - prc);
                    if (h.isInside(e))
                        prc += 1;
                });
            }
            y = y + speed - prc;
        }
        if (Game.scenes.get("Game").keyListener.isHeld('D')) {
            prc = 0;
            for (int i = 0; i < speed; i++) {
                hitList.forEach(e -> {
                    h.move(x + speed - prc, y);
                    if (h.isInside(e))
                        prc += 1;
                });
            }
            x = x + speed - prc;
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
