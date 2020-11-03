package engine;

import engine.listeners.Keyboard;
import engine.listeners.Mouse;
import engine.rendering.Graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * The class that is used to create/interact with the different windows.
 */
public class Display {

    /**
     * This variable can be used to interact with the KeyListener.
     */
    public final Keyboard keyListener = new Keyboard();
    /**
     * This variable can be used to interact with the MouseListener.
     */
    public final Mouse mouseListener = new Mouse(this);
    private String title = "Age Engine";
    private TextField textField;
    private JFrame frame;
    private Canvas canvas;
    private int width = 1280;
    private int height = 720;
    private String attachedScene;
    private boolean fullScreen = false;

    Display() {
        createWindow();
    }

    /**
     * Creates the window the user sees.
     */
    private void createWindow() {
        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        textField = new TextField();
        JPanel panel = new JPanel();

        LayoutManager overlay = new OverlayLayout(panel);
        panel.setLayout(overlay);
        canvas = new Canvas();
        canvas.setSize(width, height);
        canvas.setVisible(true);
        panel.add(canvas);
        panel.add(textField);
        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);

        canvas.addKeyListener(keyListener);
        canvas.addMouseListener(mouseListener);
    }

    /**
     * This method is used to set the state of the Window.
     *
     * @param fullScreen <p>true = FullScreen</p> <p>false = Windowed</p>
     */
    public Display setFullScreen(boolean fullScreen) {
        this.fullScreen = fullScreen;
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
        return this;
    }

    /**
     * This method is used to resize the window.
     *
     * @param width  The window width.
     * @param height The window height.
     */
    public Display setSize(int width, int height) {
        if (!fullScreen) {
            this.width = width;
            this.height = height;
            frame.setSize(width, height);
        }
        return this;
    }

    /**
     * Sets the background color of the canvas.
     *
     * @param bgColor The color that will be set.
     */
    public Display setBackgroundColor(Color bgColor) {
        canvas.setBackground(bgColor);
        return this;
    }

    /**
     * makes the window resizable
     */
    public Display makeResizable(boolean b){
        frame.setResizable(b);
        return this;
    }

    /**
     * Sets the width of the window
     * @param width The value you want the width to be set to
     */
    public Display setWidth(int width){
        this.width = width;
        frame.setSize(this.width, this.height);
        return this;
    }
    /**
     * Sets the height of the window
     * @param height The value you want the height to be set to
     */
    public Display setHeight(int height){
        this.height = height;
        frame.setSize(this.width, this.height);
        return this;
    }

    /**
     * set the window's title to a given string
     *
     * @param name the name to set the title to
     */
    public Display setTitle(String name) {
        this.title = name;
        frame.setTitle(name);
        return this;
    }

    /**
     * This method is used to attach a scene to a window. The scene will be shown in the chosen window.
     *
     * @param alias The alias of the scene.
     */
    public Display attachScene(String alias) {
        //runs the init in a loaded scene
        Game.getScene(alias).display = this;
        Game.getScene(alias).keyListener = this.keyListener;
        Game.getScene(alias).mouseListener = this.mouseListener;
        this.mouseListener.clear();
        Game.getScenes().get(alias).getObjectList().clear();
        Game.getScenes().get(alias).init();
        this.attachedScene = alias;
        return this;
    }

    /**
     * @return Returns the currently attached scene.
     */
    public String getAttachedScene() {
        return attachedScene;
    }

    /**
     * This creates the buffer-Strategy for the Graphics object. Can be interesting. But can be ignored for normal use.
     *
     * @return Returns the bufferStrategy
     */
    public BufferStrategy bs() {
        BufferStrategy bs = canvas.getBufferStrategy();
        if (bs == null) {
            canvas.createBufferStrategy(3);
            bs = canvas.getBufferStrategy();
        }
        return bs;
    }

    /**
     * @return Returns the screen width.
     */
    public int getWidth() {
        return frame.getWidth();
    }

    /**
     * @return Returns the screen height.
     */
    public int getHeight() {
        return frame.getHeight();
    }

    /**
     * This is an internal method, it shall not be used
     *
     * @return The canvas in a display.
     */
    public Canvas getCanvas() {
        return canvas;
    }

    /**
     * This is an internal method, it shall not be used
     *
     * @return The Frame of a display.
     */
    public Frame getFrame() {
        return frame;
    }

    /**
     * This is an internal method, it shall not be used
     *
     * @return The textField in a display
     */
    public TextField getTextField() {
        return textField;
    }
}
