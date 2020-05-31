package engine;

public abstract class Object {
    /**
     * This Method will be called once on creation of the object
     */
    public abstract void init();
    /**
     * The loop for the object logic
     */
    public abstract void logicLoop();
    /**
     * The loop for the object render
     */
    public abstract void renderLoop();
}
