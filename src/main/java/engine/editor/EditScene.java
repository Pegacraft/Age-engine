package engine.editor;

import engine.Game;
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
import java.awt.event.MouseEvent;
import java.io.File;

import static engine.rendering.Graphics.g;

public class EditScene extends Scene {
    Grid grid;
    private Hitbox workArea;
    private TextBox scaleBox;
    private JFileChooser chooser;
    private Environment env;
    private double scale;
    private Tile selection;

    @Override
    public void init() {
        workArea = new Hitbox(new Point(0, 0), new Point(1100, 720));
        scaleBox = new TextBox(1190, 460, 30, 20, this);
        chooser = new JFileChooser();
        grid = new Grid(0, 0, 1100, 720, 10, 10);
        env = new Environment();
        scale = 1;

        Button b = new Button(1130, 500, 100, 40)
                .addEvent(MouseButtons.LEFT_DOWN, this::mouseHandler)
                .addEvent(MouseButtons.RIGHT_DOWN, this::mouseHandler)
                .addEvent(MouseButtons.MIDDLE_DOWN, this::mouseHandler)
                .setColor(Color.cyan)
                .setText("[L]oad/Save[R]")
                .setHoverColor(Color.cyan.darker())
                .setTextColor(Color.red)
                .setFontSize(15)
                .setFont("JhengHei UI");
        Button settings = new Button(1130, 600, 100, 40)
                .addEvent(MouseButtons.LEFT_DOWN, e -> display.attachScene("Settings"))
                .setColor(Color.GREEN)
                .setText("Settings")
                .setFont("JHengHei UI");
        grid.show(Color.DARK_GRAY);
        SettingsScene scene = (SettingsScene) (Game.getScene("Settings"));
        grid.setWidth(scene.gridWidth);
        grid.setHeight(scene.gridHeight);
        addObject(grid);
        for (int i = 0; i < 10; i++)
            addObject(new Tile(1100 + (i / 5) * 90, (i % 5) * 90));
        addObject(scaleBox);
        addObject(env);
        addObject(b);
        addObject(settings);

        mouseListener.addEvent(MouseButtons.LEFT_DOWN, e -> {
            Point p = grid.toGrid(mouseListener.getMousePos());
            if (workArea.isInside(mouseListener.getMousePos()) && selection != null && selection.importPath != null)
                env.add(new Sprite(
                        p.x, p.y,
                        scale, selection.importPath, this));
        }, false);

    }

    private void mouseHandler(MouseEvent e) {
        File f = new File("src/main/resources");
        String url = f.getAbsolutePath();
        chooser.setCurrentDirectory(new File(url));
        chooser.setFileFilter(new FileNameExtensionFilter("Environments", "ini"));
        int returnValue;
        if (e.getButton() == MouseEvent.BUTTON1) returnValue = chooser.showOpenDialog(display.getCanvas());
        else if (e.getButton() == MouseEvent.BUTTON3) returnValue = chooser.showSaveDialog(display.getCanvas());
        else {
            env.reload();
            return;
        }
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String path = chooser.getSelectedFile().getPath();
            if (!path.matches(".*\\.ini")) path += ".ini";
            if (e.getButton() == MouseEvent.BUTTON1) env.loadEnv(path);
            else env.saveEnv(path);
        }
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
    }

    public void setSelection(Tile selection) {
        this.selection = selection;
    }
}
