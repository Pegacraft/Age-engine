package engine.mechanics;

import engine.Entity;
import engine.Scene;

import java.util.function.Consumer;

/**
 * This Object is used to execute a method at a certain position in the object list of a class.
 */
public class MethodObject implements Entity {
    Consumer<Scene> renderMethod;
    Consumer<Scene> logicMethod;
    Scene scene;

    /**
     * The constructor of the method object.
     *
     * @param scene The scene it's attached to.
     */
    public MethodObject(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void init() {

    }

    @Override
    public void logicLoop() {
        if (logicMethod != null)
            logicMethod.accept(scene);
    }

    @Override
    public void renderLoop() {
        if (renderMethod != null)
            renderMethod.accept(scene);
    }

    /**
     * Use this method to execute another method in the render loop.
     *
     * @param method the method you wanna execute.
     */
    public MethodObject execRenderLoop(Consumer<Scene> method) {
        renderMethod = method;
        return this;
    }

    /**
     * Use this method to execute another method in the logic loop.
     *
     * @param method the method you wanna execute.
     */
    public MethodObject execLogicLoop(Consumer<Scene> method) {
        logicMethod = method;
        return this;
    }
}
