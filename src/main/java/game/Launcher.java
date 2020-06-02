package game;

import engine.Display;
import engine.Game;

import java.awt.*;

public class Launcher {

    public static void main(String[] args) {
        Display d = Game.display();
        d.setFullScreen(false);
        d.setSize(1280, 720);
        d.setBackgroundColor(Color.WHITE);
        Game.addScene(new TestScene(), "Test");
        d.attachScene("Test");
        d.keyListener.addListener((int) ' ', TestScene::reset);

        Display d1 = Game.display("Test");
        d1.keyListener.addListener(27, e -> {
            System.exit(0);
            return false;
        });
        d1.attachScene("Test");
        Game.start();
        Game.setFrameRate(60);
    }
}
