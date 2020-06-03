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
    private final HashMap<Integer, ArrayList<Function<MouseEvent, Boolean>>> onMouseClick = new HashMap<>();
    private final HashMap<Integer, ArrayList<Function<MouseEvent, Boolean>>> onMousePress = new HashMap<>();
    private final HashMap<Integer, ArrayList<Function<MouseEvent, Boolean>>> onMouseRelease = new HashMap<>();
    private int x, y;
    private boolean held = false;
    private int mouseEvent;

    public void mouseClicked(MouseEvent e) {
        ArrayList<Function<MouseEvent, Boolean>> functions = onMouseClick.get(e.getButton());
        if (functions != null)
            for (Function<MouseEvent, Boolean> f : functions)
                if (f.apply(e)) return;
    }

    public void mousePressed(MouseEvent e) {
        held = true;
        mouseEvent = e.getButton();
        ArrayList<Function<MouseEvent, Boolean>> functions = onMousePress.get(e.getButton());
        if (functions != null)
            for (Function<MouseEvent, Boolean> f : functions)
                if (f.apply(e)) return;
    }

    public void mouseReleased(MouseEvent e) {
        held = false;
        ArrayList<Function<MouseEvent, Boolean>> functions = onMouseRelease.get(e.getButton());
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
     * @param mouseEvent The mouse Button you want to be pressed.
     * @param function   The function to be executed.
     * @param blocking   whether further processing of the key should be done
     */
    public void addClickEvent(int mouseEvent, Consumer<MouseEvent> function, boolean blocking) {
        addClickEvent(mouseEvent, e -> {
            function.accept(e);
            return blocking;
        });
    }

    /**
     * This method is used to register a function when you press a mouse button.
     *
     * @param mouseEvent The mouse Button you want to be pressed.
     * @param function   The function to be executed.
     * @param blocking   whether further processing of the key should be done
     */
    public void addPressEvent(int mouseEvent, Consumer<MouseEvent> function, boolean blocking) {
        addPressEvent(mouseEvent, e -> {
            function.accept(e);
            return blocking;
        });
    }

    /**
     * This method is used to register a function when you release a mouse button.
     *
     * @param mouseEvent The mouse Button you want to be pressed.
     * @param function   The function to be executed.
     * @param blocking   whether further processing of the key should be done
     */
    public void addReleaseEvent(int mouseEvent, Consumer<MouseEvent> function, boolean blocking) {
        addReleaseEvent(mouseEvent, e -> {
            function.accept(e);
            return blocking;
        });
    }

    /**
     * This method is used to register a function when you click a mouse button.
     *
     * @param MouseEvent The mouse Button you want to be pressed.
     * @param function   The function to be executed.
     */
    public void addClickEvent(int MouseEvent, Function<MouseEvent, Boolean> function) {
        onMouseClick.computeIfAbsent(MouseEvent, k -> new ArrayList<>());
        onMouseClick.get(MouseEvent).add(function);
    }

    /**
     * This method is used to register a function when you press a mouse button.
     *
     * @param MouseEvent The mouse Button you want to be pressed.
     * @param function   The function to be executed.
     */
    public void addPressEvent(int MouseEvent, Function<MouseEvent, Boolean> function) {
        onMousePress.computeIfAbsent(MouseEvent, k -> new ArrayList<>());
        onMousePress.get(MouseEvent).add(function);
    }

    /**
     * This method is used to register a function when you release a mouse button.
     *
     * @param MouseEvent The mouse Button you want to be pressed.
     * @param function   The function to be executed.
     */
    public void addReleaseEvent(int MouseEvent, Function<MouseEvent, Boolean> function) {
        onMouseRelease.computeIfAbsent(MouseEvent, k -> new ArrayList<>());
        onMouseRelease.get(MouseEvent).add(function);
    }

    /**
     * Use this method to check if a certain mouse button is held down.
     *
     * @param mouseEvent The mouse button that you want to check.
     * @return true/false
     */
    public boolean isHeld(int mouseEvent) {
        return (held && this.mouseEvent == mouseEvent);
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
