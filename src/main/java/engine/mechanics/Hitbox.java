package engine.mechanics;

import engine.rendering.Graphics;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Use this class to create an hitbox.
 */
public class Hitbox {

    private final ArrayList<Point> points = new ArrayList<>();
    private final ArrayList<Point> ray = new ArrayList<>();
    Point ray1 = new Point(-100, 9000);
    Point ray2 = new Point(-100, -100);
    Point ray3 = new Point(9000, -100);
    private Point prevoious = new Point(0, 0);

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
        int accuracy = 0;
        prevoious = test;
        for (Point ray : ray) {
            accuracy += isInside(test, ray) ? 1 : 0;
        }
        return accuracy >= 2;
    }

    private boolean isInside(Point test, Point ray) {
        int intersections = 0;
        Point previous = points.get(points.size() - 1);

        for (Point point : points) {
            if (intersects(point, previous, test, ray)) intersections++;
            previous = point;
        }
        return intersections % 2 == 1;
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

    private boolean intersects(Point e1, Point e2, Point v1, Point v2) {
        double d1, d2;
        double a1 = e2.y - e1.y;
        double b1 = e1.x - e2.x;
        double c1 = e2.x * e1.y - e1.x * e2.y;
        d1 = a1 * v1.x + b1 * v1.y + c1;
        d2 = a1 * v2.x + b1 * v2.y + c1;
        if (d1 > 0 && d2 > 0 || d1 < 0 && d2 < 0) return false;
        double a2 = v2.y - v1.y;
        double b2 = v1.x - v2.x;
        double c2 = v2.x * v1.y - v1.x * v2.y;
        d1 = a2 * e1.x + b2 * e1.y + c2;
        d2 = a2 * e2.x + b2 * e2.y + c2;
        if (d1 > 0 && d2 > 0 || d1 < 0 && d2 < 0) return false;
        else return a1 * b2 - a2 * b1 != 0.0;
    }

    /**
     * Use this method in the rendering loop, to show the hitbox.
     */
    public void show() {
        Point cached = points.get(points.size() - 1);
        for (Point p : ray) {
            if (isInside(prevoious, p))
                Graphics.g.setColor(Color.red);
            else Graphics.g.setColor(Color.green);
            Graphics.g.drawLine(prevoious.x, prevoious.y, p.x, p.y);
        }
        for (Point p : points) {
            if (intersects(ray.get(1), prevoious, cached, p))
                Graphics.g.setColor(Color.red);
            else Graphics.g.setColor(Color.green);
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
