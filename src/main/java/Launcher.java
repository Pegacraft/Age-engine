import display.Display;

import java.awt.*;

public class Launcher {

    public static void main(String[] args){
        Display d = new Display();
        d.setFullScreen(false);
        d.setSize(1920,1080);
        d.setBackgroundColor(Color.BLACK);
    }
}
