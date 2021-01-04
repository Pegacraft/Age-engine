package engine.mechanics;

import engine.Entity;

import java.awt.*;

import static engine.rendering.Graphics.g;

public class Grid extends Entity {
    private boolean show = false;
    private Color color;
    private int width;
    private int height;
    private int row;
    private int column;

    public Grid(int x, int y, int width, int height, int row, int column) {
        this.x = x;
        this.y = y;
        this.width = width / column;
        this.height = height / row;
        this.row = row;
        this.column = column;
    }

    @Override
    public void init() {

    }

    @Override
    public void logicLoop() {

    }

    @Override
    public void renderLoop() {
        if (show) {
            g.setColor(color);
            g.drawRect(x, y, width * column, height * row);
            for (int i = 0; i < column; i++) {
                g.drawLine(x + width * i, y, x + width * i, y + height * row);
            }
            for (int i = 0; i < row; i++) {
                g.drawLine(x, y + height * i, x + width * column, y + height * i);
            }
        }
    }

    /**
     * Use this method to lock an object to the grid
     *
     * @param point The coordinates you wanna lock
     * @return The locked point according to the grid
     */
    public Point toGrid(Point point) {
        if (point.x > x && point.y > y && point.x < x + width * column && point.y < y + height * column)
            return new Point(x + (point.x - x) / width * width, y + (point.y - y) / height * height);
        return point;
    }

    /**
     * Use this method to add an object to a certain position of the grid
     * @param x The row you want the x-variable for.
     * @return The x position the object has to be according to the grid
     */
    public int assignXToGrid(int x){
        return this.x + (height * x);
    }
    /**
     * Use this method to add an object to a certain position of the grid
     * @param y The row you want the y-variable for.
     * @return The y position the object has to be according to the grid
     */
    public int assignYToGrid(int y){
        return this.y + (width * y);
    }

    /**
     * Use this method to show the grid on screen
     *
     * @param color The color you want to show it in
     */
    public void show(Color color) {
        this.color = color;
        show = true;
    }

    /**
     * Use this method to hide the grid after showing it.
     */
    public void hide() {
        show = false;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

}
