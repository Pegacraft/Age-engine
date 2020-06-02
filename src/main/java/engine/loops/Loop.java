package engine.loops;

import engine.Game;
import engine.Object;
import engine.Scene;
import engine.rendering.Graphics;

/**
 * This is a engine internal function. It shall not be used.
 */
public class Loop implements Runnable {
    boolean running = false;
    Thread thread;
    /**
     * the time it took to calculate the last frame in ns
     */
    public static long frameTime = 1;
    public int frameRate = 60;

    @Override
    public void run() {
        init();
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
            }
        }
    }

    private void logicLoop() {
        //Loops through the active scene
        Game.displays.values().forEach(e -> {
            Scene s = Game.scenes.get(e.getAttachedScene());
            if (s != null) {
                s.logicLoop();
                s.getObjectList().forEach(Object::logicLoop);
            } else throw new IllegalStateException("no state defined for that display");
            e.mouseListener.MouseLoop(e);
        });
    }

    private void renderLoop() {
        Graphics.GraphicsLoop();
    }

    private void init() {

    }

    public synchronized void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }
}
