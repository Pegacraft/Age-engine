package engine.editor;

import engine.Entity;
import engine.Game;
import engine.listeners.MouseButtons;
import engine.mechanics.Hitbox;
import engine.rendering.Image;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static engine.rendering.Graphics.g;

public class Tile implements Entity {
    private static final int WIDTH = 90;
    private static final int HEIGHT = 90;
    private final int x;
    private final int y;
    private final BufferedImage select = Image.load("editor/Selected.png");
    private final Hitbox h;
    private final JFileChooser chooser = new JFileChooser();
    private final EditScene scene = (EditScene) Game.getScene("EditScene");
    String importPath;
    private BufferedImage preview;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        h = new Hitbox(new Point(x, y), new Point(x + WIDTH, y + HEIGHT));
        chooser.setFileFilter(new FileNameExtensionFilter("images", "png", "jpeg", "jpg", "gif"));
        //opens the import dialog
        scene.mouseListener.addEvent(MouseButtons.RIGHT_DOWN, e -> {
            if (h.isInside(scene.mouseListener.getMousePos())) {
                File f = new File("src/main/resources");
                String url = f.getAbsolutePath();
                chooser.setCurrentDirectory(new File(url));
                int returnValue = chooser.showOpenDialog(scene.display.getCanvas());
                //gets file path
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    importPath = chooser.getSelectedFile().getPath();
                    //makes path relative
                    importPath = importPath.substring(importPath.lastIndexOf("resources\\") + 10);

                    //checks if file type is compatible
                    if (getFileExtension(importPath).matches("png|jpe?g|gif"))
                        preview = Image.load(importPath);
                    else throw new IllegalArgumentException("this is not a valid image i believe");
                    System.out.println(preview);
                }
            }
        }, false);
        // selects a sprite
        scene.mouseListener.addEvent(MouseButtons.LEFT_DOWN, e -> {
            if (h.isInside(scene.mouseListener.getMousePos()))
                scene.setSelection(this);
        }, false);
    }

    @Override
    public void init() {
        // TBD
    }

    @Override
    public void logicLoop() {
        // TBD
    }

    @Override
    public void renderLoop() {
        g.setColor(Color.blue);
        g.fillRect(x, y, WIDTH, HEIGHT);
        if (preview != null)
            g.drawImage(preview, x, y, WIDTH, HEIGHT, null);
        g.setColor(Color.red);
        g.draw(h.getShape());
    }

    public void drawSelectionHUD() {
        g.drawImage(select, x, y, null);
    }

    public String getFileExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
