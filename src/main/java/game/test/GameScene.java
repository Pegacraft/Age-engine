package game.test;

import engine.Scene;
import engine.editor.menu.Button;
import engine.listeners.MouseButtons;
import engine.rendering.Graphics;

public class GameScene extends Scene {

    @Override
    public void init() {
        Player2 pl = new Player2();
        this.addObject(new Button(200, 200, 200, 50, MouseButtons.LEFT_CLICK, e -> {
        }));
        this.addObject(pl);
    }

    @Override
    public void logicLoop() {
        // why do you want this from me linter?
    }

    @Override
    public void renderLoop() {
        Graphics.g.fillRect(-100, -100, 20, 20);
    }
}
