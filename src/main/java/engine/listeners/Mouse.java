package engine.listeners;

import engine.Display;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

public class Mouse implements MouseListener {
    private final HashMap<MouseButtons, ArrayList<Function<MouseEvent, Boolean>>> onMouseEvent = new HashMap<>();
    private int x, y;
    private boolean held = false;
    private int mouseEvent;

    public void mouseClicked(MouseEvent e) {
        ArrayList<Function<MouseEvent, Boolean>> functions = onMouseEvent.get(MouseButtons.getByValues(e.getButton(), e.getID()));
        if (functions != null)
            for (Function<MouseEvent, Boolean> f : functions)
                if (f.apply(e)) return;
    }

    public void mousePressed(MouseEvent e) {
        held = true;
        mouseEvent = e.getButton();
        ArrayList<Function<MouseEvent, Boolean>> functions = onMouseEvent.get(MouseButtons.getByValues(e.getButton(), e.getID()));
        if (functions != null)
            for (Function<MouseEvent, Boolean> f : functions)
                if (f.apply(e)) return;
    }

    public void mouseReleased(MouseEvent e) {
        held = false;
        ArrayList<Function<MouseEvent, Boolean>> functions = onMouseEvent.get(MouseButtons.getByValues(e.getButton(), e.getID()));
        if (functions != null)
            for (Function<MouseEvent, Boolean> f : functions)
                if (f.apply(e)) return;
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    /**
     * Internal function. it shall not be used.
     */
    public void MouseLoop(Display display) {
        x = (int) Math.round(MouseInfo.getPointerInfo().getLocation().getX()
                - display.getCanvas().getLocationOnScreen().getX());
        y = (int) Math.round(MouseInfo.getPointerInfo().getLocation().getY()
                - display.getCanvas().getLocationOnScreen().getY());
    }

    /**
     * This method is used to register a function when you click a mouse button.
     *
     * @param button   The mouse Button you want to be pressed.
     * @param function The function to be executed.
     * @param blocking whether further processing of the key should be done
     */
    public void addEvent(MouseButtons button, Consumer<MouseEvent> function, boolean blocking) {
        addEvent(button, e -> {
            function.accept(e);
            return blocking;
        });
    }

    /**
     * This method is used to register a function when you release a mouse button.
     *
     * @param button The mouse Button you want to be pressed.
     * @param function   The function to be executed.
     */
    public void addEvent(MouseButtons button, Function<MouseEvent, Boolean> function) {
        onMouseEvent.computeIfAbsent(button, k -> new ArrayList<>());
        onMouseEvent.get(button).add(function);
    }

    /**
     * Use this method to check if a certain mouse button is held down.
     *
     * @param mouseButton The mouse button that you want to check.
     */
    public boolean isHeld(int mouseButton) {
        return (held && this.mouseEvent == mouseButton);
    }

    /**
     * Use this method to get the mouse position relative to the window.
     *
     * @return Returns a dimension with the mouse position.
     */
    public Dimension getMousePos() {
        return new Dimension(x, y);
    }
}
