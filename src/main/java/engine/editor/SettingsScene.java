package engine.editor;

import engine.Game;
import engine.Scene;
import engine.editor.menu.Button;
import engine.listeners.MouseButtons;
import engine.mechanics.TextBox;

import static engine.rendering.Graphics.g;

import java.awt.*;

public class SettingsScene extends Scene {
    public int gridWidth = 90;
    public int gridHeight = 90;
    @Override
    public void init() {
        TextBox GridWidthBox = new TextBox(90, 87, 100, 20, this);
        TextBox GridHeightBox = new TextBox(90, 127, 100, 20, this);
        GridWidthBox.setText(String.valueOf(gridWidth));
        GridHeightBox.setText(String.valueOf(gridHeight));
        Button back = new Button(20, 20, 100, 40)
                .addEvent(MouseButtons.LEFT_DOWN, e -> {
                    gridWidth = Integer.parseInt(GridWidthBox.getText());
                    gridHeight = Integer.parseInt(GridHeightBox.getText());
                    display.attachScene("EditScene");
                })
                .setColor(Color.GRAY)
                .setFont("JhengHei UI")
                .setText("Back")
                .setTextColor(Color.white);

        addObject(back);
        addObject(GridWidthBox);
        addObject(GridHeightBox);
    }

    @Override
    public void logicLoop() {

    }

    @Override
    public void renderLoop() {

        g.drawString("Grid width:", 20, 100);
        g.drawString("Grid height:", 20, 140);
    }
}
