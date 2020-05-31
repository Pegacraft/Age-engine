package engine.loops;

import engine.Game;
import engine.rendering.Graphics;

/**
 * This is a engine internal function. It shall not be used.
 */
public class Loop implements Runnable {
    boolean running = false;
    Thread thread;
    public long frame = (long) 16.6666667;

    @Override
    public void run() {
        init();
        while (running) {
            logicLoop();
            renderLoop();
            try {
                Thread.sleep(frame - System.currentTimeMillis() % frame);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void logicLoop() {
        //Loops through the active scene
        Game.displays.values().forEach(e -> {
            try {
                Game.scenes.get(e.getAttachedScene()).logicLoop();
            } catch (NullPointerException ignore) {
            }
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

    public synchronized void stop() {
        if (!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
