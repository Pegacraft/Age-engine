package engine;

import engine.loops.InputLoop;
import engine.loops.Loop;

import java.util.HashMap;

/**
 * This is the centralized object where all information is included.
 * This contains info about Displays, and future stuff
 */
public class Game {
    public static final HashMap<String, Display> displays = new HashMap<>();
    public static final HashMap<String, Scene> scenes = new HashMap<>();
    private static Loop loop;
    private static InputLoop ILoop;

    /**
     * This method starts the Loop components. Essential for game logic.
     */
    public static void start() {
        loop = new Loop();
        loop.start();
        ILoop = new InputLoop();
        ILoop.start();
    }

    /**
     * This method is used set a custom frame rate to the logic and render loop. If you don't define it with this method,
     * it'll be set to 60fps.
     *
     * @param frameRate The frame rate you want in fps.
     */
    public static void setFrameRate(int frameRate) {
        loop.frame = 1000 / frameRate;
    }

    /**
     * This function is used to register a new Scene in your Game.
     * @param scene The scene class you want to add.
     * @param alias The alias as what the class is going to be saved.
     */
    public static void addScene(Scene scene, String alias) {
        scenes.put(alias, scene);
    }

    /**
     * proxy to a display named <code>"MAIN"</code>
     *
     * @return the display named MAIN
     */
    public static Display display() {
        return display("MAIN");
    }

    /**
     * This can be used to handle multiple displays.
     * The displays are organized using their name inside of a HashMap.
     *
     * <p>If no display using that name is present, a new one will be generated.</p>
     *
     * @param name the name of the display to be returned
     * @return the display with the given name
     */
    public static Display display(String name) {
        if (displays.get(name) == null)
            displays.put(name, new Display());
        return displays.get(name);
    }
}
