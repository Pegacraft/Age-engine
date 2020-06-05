import engine.Display;
import engine.Game;
import engine.Scene;
import engine.listeners.MouseButtons;
import org.junit.Test;

import java.awt.event.MouseEvent;
import java.util.Random;

import static org.junit.Assert.*;

public class EngineTests {

    private final Random r = new Random();

    @Test
    public void MouseShouldEqual() {
        assertEquals("Mouse should be the same using both ways of access. ",
                MouseButtons.LEFT_CLICK, MouseButtons.getByValues(MouseEvent.BUTTON1, MouseEvent.MOUSE_CLICKED));
        assertEquals("Mouse should be the same using both ways of access. ",
                MouseButtons.RIGHT_CLICK, MouseButtons.getByValues(MouseEvent.BUTTON2, MouseEvent.MOUSE_CLICKED));
        assertEquals("Mouse should be the same using both ways of access. ",
                MouseButtons.LEFT_DOWN, MouseButtons.getByValues(MouseEvent.BUTTON1, MouseEvent.MOUSE_PRESSED));
        assertEquals("Mouse should be the same using both ways of access. ",
                MouseButtons.LEFT_UP, MouseButtons.getByValues(MouseEvent.BUTTON1, MouseEvent.MOUSE_RELEASED));
    }

    public void DisplayShouldStayConstant() {
        Display d = Game.display();
        assertSame("main display should stay main display. ", d, Game.display());
        assertSame("MAIN display should by display without parameter. ", d, Game.display("MAIN"));
        for (int i = 0; i <= 10; i++) {
            String name = String.valueOf(r.nextInt());
            assertSame("Display using alias should stay the same. ", Game.display(name), Game.display(name));
        }
    }

    public void ScenesShouldMatch() {
        Display d = Game.display();
        Scene s = new TestingScene();
        assertThrows("Display should not be able to attach non-existent scene. ",
                NullPointerException.class, () -> d.attachScene("TestScene"));
        Game.addScene(s, "TestScene");
        d.attachScene("TestScene");
        assertEquals("Display should keep scene attached. ", d.getAttachedScene(), "TestScene");
//        int width = Math.abs(r.nextInt(1000)), height = Math.abs(r.nextInt(1000));
//       d.setSize(width, height);
//        assertEquals("Display's width should be kept. ", width, d.getHeight());
//        assertEquals("Display's height should be kept. ", height, d.getWidth());
    }
}
