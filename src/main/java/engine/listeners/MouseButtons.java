package engine.listeners;

import java.awt.event.MouseEvent;

public enum MouseButtons {
    LEFT_CLICK(MouseEvent.BUTTON1, MouseEvent.MOUSE_CLICKED),
    LEFT_DOWN(MouseEvent.BUTTON1, MouseEvent.MOUSE_PRESSED),
    LEFT_UP(MouseEvent.BUTTON1, MouseEvent.MOUSE_RELEASED),
    RIGHT_CLICK(MouseEvent.BUTTON2, MouseEvent.MOUSE_CLICKED),
    RIGHT_DOWN(MouseEvent.BUTTON2, MouseEvent.MOUSE_PRESSED),
    RIGHT_UP(MouseEvent.BUTTON2, MouseEvent.MOUSE_RELEASED),
    MIDDLE_CLICK(MouseEvent.BUTTON3, MouseEvent.MOUSE_CLICKED),
    MIDDLE_DOWN(MouseEvent.BUTTON3, MouseEvent.MOUSE_PRESSED),
    MIDDLE_UP(MouseEvent.BUTTON3, MouseEvent.MOUSE_RELEASED);

    int button;
    int mouseEvent;

    MouseButtons(int button, int mouseEvent) {
        this.button = button;
        this.mouseEvent = mouseEvent;
    }

    public static MouseButtons getByValues(int button, int mouseEvent) {
        for (MouseButtons b : values())
            if (b.button == button && b.mouseEvent == mouseEvent)
                return b;
        return null;
    }
}
