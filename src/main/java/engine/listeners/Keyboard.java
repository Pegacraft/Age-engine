package engine.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * This handles all keyPresses. In here you have an array of booleans to show you which keys are pressed inside of the current frame.
 * use the <code>addListener</code> function to add callbacks on keyPresses.
 *
 * @see Keyboard#addListener
 */
public class Keyboard implements KeyListener {
    /**
     * all the keys currently pressed
     */
    public final boolean[] pressesKeys = new boolean[1024];
    /**
     * don't look at it, please
     */
    private final HashMap<Integer, ArrayList<Predicate<KeyEvent>>> onKeyPress = new HashMap<>();

    /**
     * internal function
     */
    public void keyTyped(KeyEvent e) {
        // unhandled event
    }

    /**
     * internal function
     */
    public void keyPressed(KeyEvent e) {
        ArrayList<Predicate<KeyEvent>> functions = onKeyPress.get(e.getKeyCode());
        if (functions != null)
            for (Predicate<KeyEvent> f : functions)
                if (f.test(e)) return;
        pressesKeys[e.getKeyCode()] = true;
    }

    /**
     * internal function
     */
    public void keyReleased(KeyEvent e) {
        pressesKeys[e.getKeyCode()] = false;
    }

    /**
     * This is the main function to be used to register function when you press a key
     *
     * @param keyCode  the code to which the function reacts
     * @param function the callback to be executed when said key gets pressed.
     *                 return values:<br>
     *                 <code>true</code>: no further execution of other callbacks will be handled<br>
     *                 <code>false</code>: all other callbacks registered will be executed (until one returns true)
     */
    public void addListener(Integer keyCode, Predicate<KeyEvent> function) {
        onKeyPress.computeIfAbsent(keyCode, k -> new ArrayList<>());
        onKeyPress.get(keyCode).add(function);
    }

    /**
     * this function server the same functionality as the addListener, but defaults to return false.
     *
     * @param keyCode  the code to which the function reacts
     * @param function the callback to be executed when said key gets pressed.
     * @param blocking if the function should disable all other processing of the key
     * @see Keyboard#addListener(Integer, Predicate)
     */
    public void addListener(Integer keyCode, Consumer<KeyEvent> function, boolean blocking) {
        addListener(keyCode, e -> {
            function.accept(e);
            return blocking;
        });
    }

    /**
     * clears all keyboard events
     */
    public void clear() {
        onKeyPress.clear();
    }

    /**
     * Use this method to check if a key is held down
     *
     * @param key The key you wanna check
     */
    public boolean isHeld(int key) {
        return pressesKeys[key];
    }
}
