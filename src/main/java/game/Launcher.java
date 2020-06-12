package game;

import engine.Game;

public class Launcher {

    public static void main(String[] args) {
////        Display d = Game.display();
////        d.setFullScreen(false);
////        d.setSize(1280, 720);
////        d.setBackgroundColor(Color.WHITE);
//        Game.addScene(new TestScene(), "Test");
//        Game.addScene(new GameScene(), "Game");
////        d.attachScene("Test");
//
//        Display d1 = Game.display("Game");
//        d1.attachScene("Game");
//        d1.setTitle("Test Game");
//        Game.start();
//        Game.setFrameRate(60);

        Game.startEditor();
    }
}
