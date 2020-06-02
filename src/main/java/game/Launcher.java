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
        Game.addScene(new TestScene(), "Test1");
        d.attachScene("Test");

        Display d1 = Game.display("Test");
        d1.attachScene("Test1");
        Game.start();
        Game.setFrameRate(60);
    }
}
