package engine.listeners;

import engine.Display;
import engine.rendering.Graphics;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.EnumMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static engine.rendering.Graphics.getCamPos;


/**
 * This handles all mouse presses.
 * use the <code>addListener</code> function to add callbacks on mouse presses.
 *
 * @see Mouse#addEvent
 */
public class Mouse implements MouseListener, MouseWheelListener {
    private final EnumMap<MouseButtons, CopyOnWriteArrayList<Predicate<MouseEvent>>> onMouseEvent =
            new EnumMap<>(MouseButtons.class);
    private final Display display;
    private int x;
    private int y;
    private boolean held = false;
    private int mouseEvent;
    private boolean isMouseInside = true;
    /**
     * negative when scrolling upwards<br>
     * positive downwards<br>
     * dependant on system setting
     */
    public int scrollDirection;
    private int scrollDirectionBuffer;
    public int scrollingSpeed;
    private int scrollingSpeedBuffer;

    public Mouse(Display display) {
        this.display = display;
        clear();
    }

    public void clear() {
        MouseButtons[] values = MouseButtons.values();
        for (MouseButtons b : values) onMouseEvent.put(b, new CopyOnWriteArrayList<>());
    }

    public void mouseClicked(MouseEvent e) {
        mouseEvent(e);
    }

    private void mouseEvent(MouseEvent e) {
        CopyOnWriteArrayList<Predicate<MouseEvent>> functions =
                onMouseEvent.get(MouseButtons.getByValues(e.getButton(), e.getID()));
        if (functions != null)
            for (Predicate<MouseEvent> f : functions)
                if (f.test(e)) return;
    }

    public void mousePressed(MouseEvent e) {
        held = true;
        mouseEvent = e.getButton();
        mouseEvent(e);
    }

    public void mouseReleased(MouseEvent e) {
        held = false;
        mouseEvent(e);
    }

    public void mouseEntered(MouseEvent e) {
        isMouseInside = true;
    }

    public void mouseExited(MouseEvent e) {
        isMouseInside = false;
    }

    /**
     * Internal function. it shall not be used.
     */
    public void mouseLoop(Display display) {
        scrollDirection = scrollDirectionBuffer;
        scrollingSpeed = scrollingSpeedBuffer;
        scrollDirectionBuffer = 0;
        scrollingSpeedBuffer = 0;

        try {
            x = (int) Math.round(MouseInfo.getPointerInfo().getLocation().getX()
                    - display.getCanvas().getLocationOnScreen().getX());
            y = (int) Math.round(MouseInfo.getPointerInfo().getLocation().getY()
                    - display.getCanvas().getLocationOnScreen().getY());
            x = (int) scaledX(x) - getCamPos().x;
            y = (int) scaledY(y) - getCamPos().y;
        } catch (IllegalComponentStateException ignore) {
        }

    }

    /**
     * This method is used to register a function when you press a mouse button.
     *
     * @param button   The mouse event on which you want the function to happen.
     * @param function The function to be executed.
     * @param blocking whether further processing of the key should be done
     * @see MouseButtons
     */
    public void addEvent(MouseButtons button, Consumer<MouseEvent> function, boolean blocking) {
        addEvent(button, e -> {
            function.accept(e);
            return blocking;
        });
    }

    /**
     * This method is used to register a function when you press a mouse button.
     *
     * @param button   The mouse event on which you want the function to happen.
     * @param function The function to be executed.
     * @see MouseButtons
     */
    public void addEvent(MouseButtons button, Predicate<MouseEvent> function) {
        onMouseEvent.get(button).add(function);
    }

    /**
     * Use this method to delete a mouseEvent of a certain object.
     *
     * @param button   the button of the trigger
     * @param function the function to be deleted
     */
    public void deleteEvent(MouseButtons button, Predicate<MouseEvent> function) {
        onMouseEvent.get(button).remove(function);
    }

    /**
     * Use this method to check if the mouse is inside a window
     */
    public boolean isMouseInside() {
        return isMouseInside;
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
     * Use this method to get the mouse position relative to the world.
     *
     * @return Returns a Point with the mouse position.
     */
    public Point getMousePos() {
        return new Point(x, y);
    }

    /**
     * Use this method to get the mouse position relative to the window.
     *
     * @return Returns a Point with the mouse position.
     */
    public Point getMousePosToCam() {
        return new Point(x + getCamPos().x, y + getCamPos().y);
    }

    private double scaledX(int x) {
        return (x / ((double) (display.getWidth()) / Graphics.getStdWidth()));
    }

    private double scaledY(int y) {
        return (y / ((double) (display.getHeight()) / Graphics.getStdHeight()));
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        scrollingSpeedBuffer = Math.abs(e.getUnitsToScroll());
        scrollDirectionBuffer = (int) Math.signum(e.getUnitsToScroll());
    }
}
