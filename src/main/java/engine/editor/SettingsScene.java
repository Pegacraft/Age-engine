package engine.editor;

import engine.Scene;
import engine.editor.menu.Button;
import engine.listeners.MouseButtons;
import engine.mechanics.TextBox;

import java.awt.*;

import static engine.rendering.Graphics.g;

public class SettingsScene extends Scene {
    int gridWidth = 90;
    int gridHeight = 90;

    @Override
    public void init() {
        TextBox gridWidthBox = new TextBox(90, 87, 100, 20, this)
                .setMatcher("[0-9]*")
                .setText(String.valueOf(gridWidth));
        TextBox gridHeightBox = new TextBox(90, 127, 100, 20, this)
                .setMatcher("[0-9]*")
                .setText(String.valueOf(gridHeight));
        Button back = new Button(20, 20, 100, 40)
                .addEvent(MouseButtons.LEFT_DOWN, e -> {
                    gridWidth = Integer.parseInt(gridWidthBox.getText());
                    gridHeight = Integer.parseInt(gridHeightBox.getText());
                    display.attachScene("EditScene");
                })
                .setColor(Color.GRAY)
                .setFont("JhengHei UI")
                .setText("Back")
                .setTextColor(Color.white);
        addObject(back);
        addObject(gridWidthBox);
        addObject(gridHeightBox);
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
