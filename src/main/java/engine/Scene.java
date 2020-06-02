package engine;

import java.util.ArrayList;

/**
 * Extend your class with that class to create a scene.
 */
public abstract class Scene {
    /**
     * This is the list, where all objects in a scene will be listed and added.
     */
    protected ArrayList<Object> ObjectList = new ArrayList<>();
    protected Display display;

    /**
     * This method will be called once at the activation of the scene.
     */
    public abstract void init();

    /**
     * The loop for the scene logic
     */
    public abstract void logicLoop();

    /**
     * The loop for the scene render
     */
    public abstract void renderLoop();
}
