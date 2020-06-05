package engine;

import engine.listeners.Keyboard;
import engine.listeners.Mouse;

import java.util.ArrayList;

/**
 * Extend your class with that class to create a scene.
 */
public abstract class Scene {
    /**
     * This is the list, where all objects in a scene will be listed and added.
     */
    private final ArrayList<Object> ObjectList = new ArrayList<>();
    /**
     * The display, where this scene is attached to.
     */
    public Display display;
    public Keyboard keyListener;
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
     * @see Scene#addObject(Object)
     * @see Scene#removeObject(Object)
     * @see Scene#getObject(int)
     */
    public ArrayList<Object> getObjectList() {
        return ObjectList;
    }

    /**
     * @param obj The object to be added.
     */
    protected void addObject(Object obj) {
        if (obj == null)
            throw new NullPointerException("The object can not be null");
        obj.init();
        ObjectList.add(obj);
    }

    /**
     * @param obj The object to be removed
     */
    protected void removeObject(Object obj) {
        ObjectList.remove(obj);
    }

    /**
     * @param index The position in the ObjectList
     * @return The wanted object
     */
    protected Object getObject(int index) {
        return ObjectList.get(index);
    }
}
