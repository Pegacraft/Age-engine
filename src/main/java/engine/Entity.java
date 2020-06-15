package engine;

public interface Entity {
    /**
     * This Method will be called once on creation of the object
     */
    void init();

    /**
     * The loop for the object logic
     */
    void logicLoop();

    /**
     * The loop for the object render
     */
    void renderLoop();
}
