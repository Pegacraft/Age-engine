package engine;

import java.util.HashMap;

public class Game {

    private static final HashMap<String, Display> displays = new HashMap<>();

    /**
     * proxy to a display named <code>"MAIN"</code>
     *
     * @return the display named MAIN
     */
    public static Display d() {
        return d("MAIN");
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
    public static Display d(String name) {
        if (displays.get(name) == null)
            displays.put(name, new Display());
        return displays.get(name);
    }
}
