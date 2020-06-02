package engine.listeners;

import engine.Display;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class Mouse implements MouseListener {
    private int x, y;
    private boolean hold = false;
    private int mouseEvent;
    private final HashMap<Integer, ArrayList<Consumer<MouseEvent>>> onMouseClick = new HashMap<>();
    private final HashMap<Integer, ArrayList<Consumer<MouseEvent>>> onMousePress = new HashMap<>();
    private final HashMap<Integer, ArrayList<Consumer<MouseEvent>>> onMouseRelease = new HashMap<>();

    @Override
    public void mouseClicked(MouseEvent e) {
        ArrayList<Consumer<MouseEvent>> functions = onMouseClick.get(e.getButton());
        if (functions != null)
            for (Consumer<MouseEvent> f : functions)
                f.accept(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        hold = true;
        mouseEvent = e.getButton();
        ArrayList<Consumer<MouseEvent>> functions = onMousePress.get(e.getButton());
        if (functions != null)
            for (Consumer<MouseEvent> f : functions)
                f.accept(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        hold = false;
        ArrayList<Consumer<MouseEvent>> functions = onMouseRelease.get(e.getButton());
        if (functions != null)
            for (Consumer<MouseEvent> f : functions)
                f.accept(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
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
     * @param MouseEvent The mouse Button you want to be pressed.
     * @param function   The function to be executed.
     */
    public void addClickEvent(int MouseEvent, Consumer<MouseEvent> function) {
        onMouseClick.computeIfAbsent(MouseEvent, k -> new ArrayList<>());
        onMouseClick.get(MouseEvent).add(function);
    }

    /**
     * This method is used to register a function when you press a mouse button.
     *
     * @param MouseEvent The mouse Button you want to be pressed.
     * @param function   The function to be executed.
     */
    public void addPressEvent(int MouseEvent, Consumer<MouseEvent> function) {
        onMousePress.computeIfAbsent(MouseEvent, k -> new ArrayList<>());
        onMousePress.get(MouseEvent).add(function);
    }

    /**
     * This method is used to register a function when you release a mouse button.
     *
     * @param MouseEvent The mouse Button you want to be pressed.
     * @param function   The function to be executed.
     */
    public void addReleaseEvent(int MouseEvent, Consumer<MouseEvent> function) {
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
        return (hold && this.mouseEvent == mouseEvent);
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
