package engine.loops;

import engine.Entity;
import engine.Game;
import engine.Scene;
import engine.mechanics.EntityList;
import engine.rendering.Graphics;

import java.util.ConcurrentModificationException;

/**
 * This is a engine internal function. It shall not be used.
 */
public class Loop implements Runnable {
    /**
     * the time it took to calculate the last frame in ns
     */
    public static long frameTime = 1;
    private int frameRate = 60;
    private boolean running = false;

    @Override
    public void run() {
        while (running) {
            long startTime = System.nanoTime();
            logicLoop();
            renderLoop();
            try {
                frameTime = (System.nanoTime() - startTime);
                long toWait = (long) 1E9 / frameRate - frameTime;
                if (toWait < 0) toWait = 0;
                //noinspection BusyWait
                Thread.sleep((long) (toWait / 1E6), (int) (toWait % 1E6));
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    private void logicLoop() {
        //Loops through the active scene
        Game.getDisplays().values().forEach(e -> {
            Scene s = Game.getScene(e.getAttachedScene());
            if (s != null) {
                try {
                    s.logicLoop();
                    s.getObjectList().forEach(Entity::logicLoop);
                    s.getObjectList().forEach(entity -> {
                        entity.getObjectList().forEach(Entity::logicLoop);
                    });
                } catch (ConcurrentModificationException ignore) {
                    System.out.println(ignore);
                }
            }
            e.mouseListener.mouseLoop(e);
        });
    }

    private void renderLoop() {
        Graphics.graphicsLoop();
    }

    public synchronized void start() {
        if (running)
            return;
        running = true;
        Thread thread = new Thread(this);
        thread.start();
    }

    public void setFrameRate(int frameRate) {
        this.frameRate = frameRate;
    }
}
