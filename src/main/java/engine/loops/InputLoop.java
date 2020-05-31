package engine.loops;

/**
 * This is a engine internal function. It shall not be used.
 */
public class InputLoop implements Runnable {
    boolean running;
    Thread thread;

    @Override
    public void run() {
        init();
        while (running)
            inputLoop();
    }

    private void init() {

    }

    private void inputLoop() {

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
