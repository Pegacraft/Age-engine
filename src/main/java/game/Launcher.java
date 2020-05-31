package game;

import engine.Display;
import engine.Game;

import java.awt.*;

public class Launcher {

    public static void main(String[] args) {
        Display d = Game.d();
        d.setFullScreen(false);
        d.setSize(1280, 720);
        d.setBackgroundColor(Color.BLACK);
        d = Game.d("secondary");
        d.setFullScreen(false);
        d.setSize(1280, 720);
        d.setBackgroundColor(Color.BLACK);
    }
}
