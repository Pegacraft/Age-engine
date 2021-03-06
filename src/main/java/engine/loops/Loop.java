package engine.loops;

import engine.Entity;
import engine.Game;
import engine.Scene;
import engine.rendering.Graphics;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

/**
 * This is a engine internal function. It shall not be used.
 */
public class Loop implements Runnable {
    /**
     * the time it took to calculate the last frame in ns
     */
    private static long calculationTime = 1;
    public static int frameTime = 0;
    private int frameRate = 60;
    private boolean running = false;
    private long lastFrame = System.nanoTime();

    @Override
    public void run() {
        while (running) {
            frameTime = (int) (System.nanoTime() - lastFrame);
            long startTime = lastFrame = System.nanoTime();
            logicLoop();
            renderLoop();
            try {
                calculationTime = (System.nanoTime() - startTime);
                long toWait = (long) 1E9 / frameRate - calculationTime;
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
                    List<Entity> queue = new ArrayList<>(s.getObjectList());
                    while (!queue.isEmpty()) {
                        Entity ent = queue.remove(0);
                        if (ent.enabled) {
                            ent.logicLoop();
                            queue.addAll(ent.getObjectList());
                        }
                    }
                } catch (ConcurrentModificationException ignore) {
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
