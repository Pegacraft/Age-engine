package engine.editor;

import engine.Game;
import engine.Object;
import engine.listeners.MouseButtons;
import engine.mechanics.Hitbox;
import engine.rendering.Image;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static engine.rendering.Graphics.g;

public class Tile extends Object {
    private final int x, y;
    private final int width = 90, height = 90;
    private final BufferedImage select = Image.load("editor/Selected.png");
    private final Hitbox h;
    private final JFileChooser chooser = new JFileChooser();
    private final EditScene scene = (EditScene) Game.scenes.get("EditScene");
    String importPath;
    boolean selected = false;
    private BufferedImage preview;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        h = new Hitbox(new Point(x, y), new Point(x + width, y + height));
        chooser.setFileFilter(new FileNameExtensionFilter("images", "png", "jpeg", "jpg", "gif"));
        //opens the import dialog
        Game.scenes.get("EditScene").mouseListener.addEvent(MouseButtons.RIGHT_DOWN, e -> {
            if (h.isInside(Game.scenes.get("EditScene").mouseListener.getMousePos())) {
                File f = new File("src/main/resources");
                String url = f.getAbsolutePath();
                chooser.setCurrentDirectory(new File(url));
                int returnValue = chooser.showOpenDialog(Game.scenes.get("EditScene").display.getCanvas());
                //gets file path
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    importPath = chooser.getSelectedFile().getPath();
                    //makes path relative
                    importPath = importPath.substring(importPath.lastIndexOf("resources\\") + 10);

                    //checks if file type is compatible
                    if (getFileExtension(importPath).matches("png|jpe?g|gif"))
                        preview = Image.load(importPath);
                    else throw new IllegalArgumentException("this is not a valid image i believe");
                }
            }
        }, false);
        // selects a sprite
        Game.scenes.get("EditScene").mouseListener.addEvent(MouseButtons.LEFT_DOWN, e -> {
            if (h.isInside(scene.mouseListener.getMousePos()))
                scene.selection = this;
        }, false);
    }

    @Override
    public void init() {
    }

    @Override
    public void logicLoop() {
    }

    @Override
    public void renderLoop() {
        g.setColor(Color.blue);
        g.fillRect(x, y, width, height);
        if (preview != null)
            g.drawImage(preview, x, y, width, height, null);
        g.setColor(Color.red);
        g.draw(h.shape);
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
