package engine;

import engine.editor.EditScene;
import engine.loops.Loop;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the centralized object where all information is included.
 * This contains info about Displays, and future stuff
 */
public class Game {
    private static final Map<String, Display> displays = new HashMap<>();
    private static final Map<String, Scene> scenes = new HashMap<>();
    private static Loop loop;

    private Game() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * This method starts the Loop components. Essential for game logic.
     */
    public static void start() {
        loop = new Loop();
        loop.start();
    }

    /**
     * This method is used set a custom frame rate to the logic and render loop. If you don't define it with this method,
     * it'll be set to 60fps.
     *
     * @param frameRate The frame rate you want.
     */
    public static void setFrameRate(int frameRate) {
        loop.setFrameRate(frameRate);
    }

    /**
     * This function is used to register a new Scene in your Game.
     *
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
        displays.computeIfAbsent(name, k -> new Display());
        return displays.get(name);
    }

    /**
     * start the environment-editor using a new display called "editor"
     */
    public static void startEditor() {
        Display display = Game.display("editor");
        Game.addScene(new EditScene(), "EditScene");
        display.attachScene("EditScene");
        Game.start();
        Game.setFrameRate(60);
        display.setTitle("Map Editor");
    }

    /**
     * get a scene using it's alias
     *
     * @param alias the alias to look for
     * @return the wanted scene
     */
    public static Scene getScene(String alias) {
        return scenes.get(alias);
    }

    /**
     * get the sceneMap (don't use unless you know what you're doing)
     *
     * @return the scenes map
     */
    public static Map<String, Scene> getScenes() {
        return scenes;
    }

    /**
     * get the displayMap (don't use unless you know what you're doing)
     *
     * @return the displays map
     */
    public static Map<String, Display> getDisplays() {
        return displays;
    }
}
