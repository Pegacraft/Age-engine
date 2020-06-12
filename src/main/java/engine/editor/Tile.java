package engine.editor;

import engine.Game;
import engine.Object;
import engine.listeners.MouseButtons;
import engine.mechanics.Hitbox;
import engine.rendering.Image;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

import static engine.rendering.Graphics.g;

public class Tile extends Object {
    int x, y;
    int width = 90, height = 90;
    BufferedImage select = Image.load("editor/Selected.png");
    public BufferedImage preview;
    public boolean selected = false;
    Hitbox h;
    Hitbox workArea;
    JFileChooser chooser = new JFileChooser();
    String importPath;

    public Tile(int x, int y, Hitbox workArea) {
        this.x = x;
        this.y = y;
        this.workArea = workArea;
        h = new Hitbox(new Point(x, y), new Point(x + width, y + height));
        //opens the import dialog
        Game.scenes.get("EditScene").mouseListener.addEvent(MouseButtons.RIGHT_DOWN, e -> {
            if (h.isInside(Game.scenes.get("EditScene").mouseListener.getMousePos())) {
                File f = new File("src/main/resources");
                String url = f.getAbsolutePath();
                System.out.println(url);
                chooser.setCurrentDirectory(new File(url));
                int returnValue = chooser.showOpenDialog(Game.scenes.get("EditScene").display.getCanvas());
                //gets file path
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    importPath = chooser.getSelectedFile().getPath();
                    //makes path relative
                    importPath = importPath.substring(importPath.lastIndexOf("resources\\") + 10);

                    //checks if file type is compatible
                    if (getFileExtension(importPath).equals("png") || getFileExtension(importPath).equals("jpg") ||
                            getFileExtension(importPath).equals("jpeg") || getFileExtension(importPath).equals("gif"))
                        preview = Image.load(importPath);
                }
            }
        }, false);
        // selects a sprite
        Game.scenes.get("EditScene").mouseListener.addEvent(MouseButtons.LEFT_DOWN, e -> {
            if (h.isInside(Game.scenes.get("EditScene").mouseListener.getMousePos()))
                selected = true;
            else if (!workArea.isInside(Game.scenes.get("EditScene").mouseListener.getMousePos())) {
                selected = false;
            }
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
        if (selected)
            g.drawImage(select, x, y, null);
        g.setColor(Color.red);
        g.draw(h.shape);
    }

    public String getFileExtension(String fullName) {

        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
}
