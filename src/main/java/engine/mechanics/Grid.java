package engine.mechanics;

import java.awt.*;

import static engine.rendering.Graphics.g;

public class Grid {

    private int x;
    private int y;
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
     * Use this method to show the grid on screen
     *
     * @param color The color you want to show it in
     */
    public void show(Color color) {
        g.setColor(color);
        g.drawRect(x, y, width * column, height * row);
        for (int i = 0; i < column; i++) {
            g.drawLine(x + width * i, y, x + width * i, y + height * row);
        }
        for (int i = 0; i < row; i++) {
            g.drawLine(x, y + height * i, x + width * column, y + height * i);
        }
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
