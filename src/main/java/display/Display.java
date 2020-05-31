package display;

import javax.swing.*;
import java.awt.*;

public class Display {

    private JFrame frame;
    private Canvas canvas;
    public String title = "Age Engine";
    public int width = 1280, height = 720;

    public Display() {
        createWindow();

    }

    private void createWindow() {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        canvas = new Canvas();
        canvas.setSize(width, height);
        canvas.setVisible(true);

        frame.add(canvas);
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * This method is used to set the state of the Window.
     *
     * @param fullScreen <p>true = FullScreen</p> <p>false = Windowed</p>
     */
    public void setFullScreen(boolean fullScreen) {
        frame.dispose();
        if (fullScreen) {
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            frame.setUndecorated(true);
        } else {
            frame.setExtendedState(Frame.NORMAL);
            frame.setSize(width, height);
            frame.setUndecorated(false);
        }

        frame.setVisible(true);
    }

    /**
     * This method is used to resize the window.
     *
     * @param width  The window width.
     * @param height The window height.
     */
    public Display setSize(int width, int height) {
        this.width = width;
        this.height = height;
        frame.setSize(width, height);
        return this;
    }

    /**
     * Sets the background color of the canvas.
     * @param BgColor The color that will be set.
     */
    public Display setBackgroundColor(Color BgColor) {
        canvas.setBackground(BgColor);
        return this;
    }
}
