package engine.editor;

import engine.Scene;
import engine.editor.menu.Button;
import engine.editor.menu.Sprite;
import engine.listeners.MouseButtons;
import engine.mechanics.Grid;
import engine.mechanics.Hitbox;
import engine.mechanics.TextBox;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.InvalidPropertiesFormatException;

import static engine.rendering.Graphics.g;

public class EditScene extends Scene {

    private final Hitbox workArea = new Hitbox(new Point(0, 0), new Point(1100, 720));
    private final TextBox scaleBox = new TextBox(1190, 460, 30, 20, this);
    private final TextBox gridWidth = new TextBox(1190, 460, 30, 20, this);
    private final TextBox gridHeight = new TextBox(1190, 460, 30, 20, this);
    private final TextBox gridRow = new TextBox(1190, 460, 30, 20, this);
    private final TextBox gridColumn = new TextBox(1190, 460, 30, 20, this);
    private final JFileChooser chooser = new JFileChooser();
    private final Grid grid = new Grid(0, 0, 1100, 720, 10, 10);
    private final Environment env = new Environment();
    private double scale = 1;
    private Tile selection;

    @Override
    public void init() {
        for (int i = 0; i < 10; i++)
            addObject(new Tile(1100 + (i / 5) * 90, (i % 5) * 90));
        addObject(scaleBox);
        addObject(env);

        addObject(new Button(1150, 500, 80, 40, MouseButtons.LEFT_DOWN, e -> {
            File f = new File("src/main/resources");
            String url = f.getAbsolutePath();
            chooser.setCurrentDirectory(new File(url));
            chooser.setFileFilter(new FileNameExtensionFilter("Environments", "ini"));

            int returnValue = chooser.showSaveDialog(display.getCanvas());
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getPath();
                if (!path.matches(".*\\.ini")) path += ".ini";
                env.saveEnv(path);
            }
        }));
        addObject(new Button(1150, 600, 80, 40, MouseButtons.LEFT_DOWN, e -> {
            File f = new File("src/main/resources");
            String url = f.getAbsolutePath();
            chooser.setCurrentDirectory(new File(url));
            chooser.setFileFilter(new FileNameExtensionFilter("Environments", "ini"));

            int returnValue = chooser.showOpenDialog(display.getCanvas());
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String path = chooser.getSelectedFile().getPath();
                if (!path.matches(".*\\.ini")) path += ".ini";
                try {
                    env.loadEnv(path);
                } catch (InvalidPropertiesFormatException ex) {
                    ex.printStackTrace();
                }
            }
        }));

        mouseListener.addEvent(MouseButtons.LEFT_DOWN, e -> {
            Point p = grid.toGrid(mouseListener.getMousePos());
            if (workArea.isInside(mouseListener.getMousePos()) && selection != null && selection.importPath != null)
                env.add(new Sprite(
                        p.x, p.y,
                        scale, selection.importPath, this));
        }, false);

    }

    @Override
    public void logicLoop() {
        try {
            scale = Double.parseDouble(scaleBox.getText());
        } catch (NumberFormatException ignore) { //
        }
    }

    @Override
    public void renderLoop() {
        if (selection != null)
            selection.drawSelectionHUD();
        g.setColor(Color.BLACK);
        g.drawString("Scale:", 1150, 475);
        g.draw(workArea.getShape());
        grid.show(Color.DARK_GRAY);
    }

    public void setSelection(Tile selection) {
        this.selection = selection;
    }
}