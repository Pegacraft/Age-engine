package engine;

import engine.listeners.Keyboard;
import engine.listeners.Mouse;

import java.util.ArrayList;
import java.util.List;

/**
 * Extend your class with that class to create a scene.
 */
public abstract class Scene {
    /**
     * This is the list, where all objects in a scene will be listed and added.
     */
    private final List<Entity> objectList = new ArrayList<>();
    /**
     * The display, where this scene is attached to.
     */
    public Display display;
    /**
     * The key listener, where this scene is attached to.
     */
    public Keyboard keyListener;
    /**
     * The mouse listener, where this scene is attached to.
     */
    public Mouse mouseListener;

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

    /**
     * @return Returns the Object list of an scene. Its not recommended to use, because there are simpler ways
     * e. g. the <code>scene.addObject</code> method, or the <code>scene.getObject</code> method.
     * @see Scene#addObject(Entity)
     * @see Scene#removeObject(Entity)
     * @see Scene#getObject(int)
     */
    public List<Entity> getObjectList() {
        return objectList;
    }

    /**
     * @param obj The object to be added.
     */
    public void addObject(Entity obj) {
        if (obj == null)
            throw new NullPointerException("The object can not be null");
        obj.init();
        objectList.add(obj);
    }

    /**
     * @param obj The object to be removed
     */
    protected void removeObject(Entity obj) {
        objectList.remove(obj);
    }

    /**
     * @param index The position in the ObjectList
     * @return The wanted object
     */
    protected Entity getObject(int index) {
        return objectList.get(index);
    }
}
