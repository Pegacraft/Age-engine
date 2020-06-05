package engine.mechanics;

import engine.Game;
import engine.rendering.Graphics;
import game.test.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Use this class to create an hitbox.
 */
public class Hitbox {

    private boolean collided = false;
    private final ArrayList<Point> points = new ArrayList<>();
    private final ArrayList<Dimension> ray = new ArrayList<>();
    Dimension ray1 = new Dimension(-100, -90);
    Dimension ray2 = new Dimension(-100, -50);
    Dimension ray3 = new Dimension(-100, -10);

    private int accuracy = 0;

    /**
     * The constructor of the hitbox class. It takes an infinite amount of points which represent an n-shape. If only two
     * points are given, it'll add two points, to create a rectangle.
     *
     * @param points The points in form of a dimension.
     */
    public Hitbox(Point... points) {
        ray.add(ray1);
        ray.add(ray2);
        ray.add(ray3);
        Collections.addAll(this.points, points);
        if (this.points.size() == 0)
            throw new IllegalArgumentException("This hitbox has 0 points added");
        if (this.points.size() == 2) {
            Point p1 = this.points.get(0);
            Point p2 = this.points.get(1);

            this.points.set(0, p1);
            this.points.set(1, new Point(p1.x, p2.y));
            this.points.add(2, p2);
            this.points.add(3, new Point(p2.x, p1.y));
        }
    }

    /**
     * This method is used to check if a certain point is inside of the hitbox.
     *
     * @param test The position that should be tested as an dimension.
     */
    public boolean isInside(Point test) {
        accuracy = 0;
        for (Dimension ray : ray) {

            collided = false;
            int intersections = 0;
            Point previous = points.get(points.size() - 1);

            for (Point point : points) {
                if (intersects(
                        new Dimension(point.x, point.y),
                        new Dimension(previous.x, previous.y),
                        new Dimension(test.x, test.y), ray)
                ) intersections++;
                previous = point;
            }
            if (intersections % 2 == 1) {
                accuracy += 1;
            }
            if (accuracy >= 2) {
                collided = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Use this method to test if another hitbox collides with this one.
     *
     * @param test The hitbox it should colide with.
     */
    public boolean isInside(Hitbox test) {
        boolean b = false;
        for (Point point : points) {
            if (test.isInside(point))
                b = true;
        }
        for (Point point : test.points) {
            if (isInside(point))
                b = true;
        }
        return b;
    }

    private boolean intersects(Dimension e1, Dimension e2, Dimension v1, Dimension v2) {
        double d1, d2;
        double a1 = Math.round(e2.height - e1.height);
        double b1 = Math.round(e1.width - e2.width);
        double c1 = Math.round(e2.width * e1.height - (e1.width * e2.height));
        d1 = a1 * v1.width + b1 * v1.height + c1;
        d2 = a1 * v2.width + b1 * v2.height + c1;
        if (d1 > 0 && d2 > 0) return false;
        if (d1 < 0 && d2 < 0) return false;
        double a2 = Math.round(v2.height - v1.height);
        double b2 = Math.round(v1.width - v2.width);
        double c2 = Math.round(v2.width * v1.height - (v1.width * v2.height));
        d1 = a2 * e1.width + b2 * e1.height + c2;
        d2 = a2 * e2.width + b2 * e2.height + c2;
        if (d1 > 0 && d2 > 0) return false;
        if (d1 < 0 && d2 < 0) return false;
        else return a1 * b2 - a2 * b1 != 0.0;
    }

    /**
     * Use this method in the rendering loop, to show the hitbox.
     */
    public void show() {
        Player pl = (Player) Game.scenes.get("Game").getObjectList().get(0);
        Point cached = points.get(points.size() - 1);
        if (collided)
            Graphics.g.setColor(Color.red);
        else
            Graphics.g.setColor(Color.green);
        for (Point p : points) {
            Graphics.g.drawLine(cached.x, cached.y, p.x, p.y);
            cached = p;
        }
    }

    /**
     * Use this method to move the hitbox to a different location.
     * The first point set, will be the anchor points for the other points.
     *
     * @param x The x position you want to move in.
     * @param y The y pos you want to move in.
     */
    public void move(int x, int y) {
        int xDiff = x - points.get(0).x;
        int yDiff = y - points.get(0).y;

        for (Point p : points) {
            p.x += xDiff;
            p.y += yDiff;
        }
    }

    /**
     * Use this method to change one hitbox point.
     *
     * @param index    The point you want to change (keep in mind, it starts at 0)
     * @param newPoint The point you want to overwrite it with.
     */
    public void rePosPoint(int index, Point newPoint) {
        points.set(index, newPoint);
    }
}
