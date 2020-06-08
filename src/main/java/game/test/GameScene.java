package game.test;

import engine.Scene;
import engine.rendering.Animation;
import engine.rendering.Image;

public class GameScene extends Scene {
    Animation animation;

    @Override
    public void init() {
        Player2 pl = new Player2();
        this.addObject(new Button());
        this.addObject(pl);
        animation = new Animation(Image.load("testSheet.png"), 20, 4, 300, 300, 20, null);
    }

    @Override
    public void logicLoop() {

    }

    @Override
    public void renderLoop() {
        animation.playAnimation();
    }
}
