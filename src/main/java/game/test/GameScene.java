package game.test;

import engine.Scene;

public class GameScene extends Scene {
    @Override
    public void init() {
        this.addObject(new Player());
    }

    @Override
    public void logicLoop() {

    }

    @Override
    public void renderLoop() {

    }
}
