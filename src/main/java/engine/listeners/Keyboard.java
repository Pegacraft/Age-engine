package engine.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Function;

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
    private final HashMap<Integer, ArrayList<Function<KeyEvent, Boolean>>> onKeyPress = new HashMap<>();

    /**
     * internal function
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * internal function
     */
    @Override
    public void keyPressed(KeyEvent e) {
        ArrayList<Function<KeyEvent, Boolean>> functions = onKeyPress.get(e.getKeyCode());
        if (functions != null)
            for (Function<KeyEvent, Boolean> f : functions)
                if (f.apply(e)) return;
        pressesKeys[e.getKeyCode()] = true;
    }

    /**
     * internal function
     */
    public void keyReleased(KeyEvent e) {
        pressesKeys[e.getKeyCode()] = true;
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
    public void addListener(Integer keyCode, Function<KeyEvent, Boolean> function) {
        onKeyPress.computeIfAbsent(keyCode, k -> new ArrayList<>());
        onKeyPress.get(keyCode).add(function);
    }
}
