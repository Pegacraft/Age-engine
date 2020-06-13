package engine.mechanics;

import java.awt.*;

import static engine.rendering.Graphics.g;

public class Grid {

    private int x, y, width, height, row, column;

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
        int Px = point.x - x;
        int Py = point.y - y;
        for (int i = column - 1; i >= 0; i--) {
            if (width * i <= point.x - x) {
                Px = width * i;
                break;
            }
        }
        for (int i = row - 1; i >= 0; i--) {
            if (height * i <= point.y - y) {
                Py = height * i;
                break;
            }
        }
        return new Point(x + Px, y + Py);
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
            g.drawLine(width * i, y, width * i, height * row);
        }
        for (int i = 0; i < row; i++) {
            g.drawLine(x, height * i, width * column, height * i);
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
