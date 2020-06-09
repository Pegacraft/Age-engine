package engine.mechanics;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Use this class to create an hitbox.
 */
public class Hitbox {

    public Shape shape;
    private Shape original;

    /**
     * The constructor of the hitbox class. It takes an infinite amount of points which represent an n-shape. If only two
     * points are given, it'll add two points, to create a rectangle.
     *
     * @param points The points in form of a dimension.
     */
    public Hitbox(Point... points) {
        switch (points.length) {
            case 0:
            case 1:
                throw new IllegalArgumentException("invalid number of Points");
            case 2:
                shape = new Rectangle(points[0], new Dimension(points[1].x - points[0].x, points[1].y - points[0].y));
                return;
            default:
                shape = new Polygon();
                for (Point p : points) {
                    ((Polygon) shape).addPoint(p.x, p.y);
                }
        }
    }

    /**
     * This method is used to check if a certain point is inside of the hitbox.
     *
     * @param test The position that should be tested as an dimension.
     */
    public boolean isInside(Point test) {
        return shape.contains(test);
    }

    /**
     * Use this method to test if another hitbox collides with this one.
     *
     * @param test The hitbox it should collide with.
     */
    public boolean isInside(Hitbox test) {
        if (!shape.getBounds2D().intersects(test.shape.getBounds2D())) return false;
        if (shape instanceof Rectangle && test.shape instanceof Rectangle) return true;
        Polygon poly = null;
        if (shape instanceof Polygon && test.shape instanceof Polygon) {
            poly = (Polygon) shape;
            Polygon other = (Polygon) test.shape;
            for (int i = 0; i < other.npoints; i++)
                if (poly.contains(other.xpoints[i], other.ypoints[i])) return true;
            for (int i = 0; i < poly.npoints; i++)
                if (other.contains(poly.xpoints[i], poly.ypoints[i])) return true;
            return false;
        }

        Rectangle2D rect = null;
        if (shape instanceof Rectangle) {
            poly = (Polygon) test.shape;
            rect = (Rectangle2D) shape;
        }
        if (test.shape instanceof Rectangle) {
            assert shape instanceof Polygon;
            poly = (Polygon) shape;
            rect = (Rectangle2D) test.shape;
        }
        if (rect == null) throw new NullPointerException("wtf did you do to achieve this?");
        return poly.intersects(rect);
    }

    /**
     * Use this method to move the hitbox to a different location.
     * The first point set, will be the anchor points for the other points.
     *
     * @param x The x position you want to move in.
     * @param y The y pos you want to move in.
     */
    public void move(int x, int y) {
        if (shape instanceof Polygon) {
            Polygon poly = (Polygon) shape;
            int xDiff = x - poly.xpoints[0];
            int yDiff = y - poly.ypoints[0];
            poly.translate(xDiff, yDiff);
            return;
        }
        if (shape instanceof Rectangle)
            ((Rectangle) shape).setLocation(x, y);
    }

    /**
     * rotate the hitbox by a given angle
     *
     * @param angle the angle to rotate by in radians
     */
    public void rotate(double angle) {
        if (shape instanceof Rectangle) {
            Rectangle rect = (Rectangle) shape;
            Point p1 = new Point(rect.x, rect.y);
            Point p2 = new Point(rect.x + rect.width, rect.y + rect.height);
            Polygon poly = new Polygon();
            poly.addPoint(p1.x, p1.y);
            poly.addPoint(p2.x, p1.y);
            poly.addPoint(p2.x, p2.y);
            poly.addPoint(p1.x, p2.y);
            shape = poly;
        }
        if (shape instanceof Polygon) {
            Polygon poly = (Polygon) shape;
            Polygon next = new Polygon();
            if (original == null)
                original = new Polygon(poly.xpoints, poly.ypoints, poly.npoints);
            Polygon orig = (Polygon) original;
            AffineTransform transform = AffineTransform.getRotateInstance(angle);
            for (int i = 0; i < poly.npoints; i++) {
                Point2D p = transform.transform(new Point(orig.xpoints[i], orig.ypoints[i]), null);
                next.addPoint((int) p.getX(), (int) p.getY());
            }
            shape = next;
        }
    }
}
