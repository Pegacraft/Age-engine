package engine.mechanics;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

/**
 * Use this class to create an hitbox.
 */
public class Hitbox {

    private Polygon shape;
    private Polygon original;
    private double currentRotation = 0;
    private int[] originalX, originalY;
    private int centerX = 0, centerY = 0;

    /**
     * The constructor of the hitbox class. It takes an infinite amount of points which represent an n-shape. If only two
     * points are given, it'll add two points, to create a rectangle.
     *
     * @param points The points in form of a dimension.
     */
    public Hitbox(Point... points) {
        shape = new Polygon();
        switch (points.length) {
            case 0:
            case 1:
                throw new IllegalArgumentException("invalid number of Points");
            case 2:
                shape.addPoint(points[0].x, points[0].y);
                shape.addPoint(points[1].x, points[0].y);
                shape.addPoint(points[1].x, points[1].y);
                shape.addPoint(points[0].x, points[1].y);
                originalX = shape.xpoints.clone();
                originalY = shape.ypoints.clone();
                return;
            default:
                for (Point p : points) {
                    shape.addPoint(p.x, p.y);
                }
        }
        originalX = shape.xpoints.clone();
        originalY = shape.ypoints.clone();
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

        return isInside(shape, test.shape) || isInside(test.shape, shape);
    }

    /**
     * internal method to check if points of one polygon are inside of another
     *
     * @param a the points to be checked
     * @param b the polygon in which they should be checked
     * @return if points of a lie within b
     */
    private boolean isInside(Polygon a, Polygon b) {
        for (int i = 0; i < a.npoints; i++)
            if (b.contains(a.xpoints[i], a.ypoints[i])) return true;
        return false;
    }

    /**
     * Use this method to move the hitbox to a different location.
     * The first point set, will be the anchor points for the other points.
     *
     * @param x The x position you want to move in.
     * @param y The y pos you want to move in.
     */
    public void move(int x, int y) {
        int[] xValues = originalX.clone();
        int[] yValues = originalY.clone();
        int nValues = shape.npoints;
        int xOffset = x - originalX[0];
        int yOffset = y - originalY[0];

        for (int i = 0; i < nValues; i++) {
            xValues[i] += xOffset;
            yValues[i] += yOffset;
        }

        shape = new Polygon(xValues, yValues, nValues);
        originalX = shape.xpoints.clone();
        originalY = shape.ypoints.clone();
        original = new Polygon(shape.xpoints, shape.ypoints, shape.npoints);
        rotate(currentRotation, centerX, centerY);
    }

    /**
     * rotate the hitbox by a given angle
     *
     * @param angle   the angle to rotate by in radians
     * @param centerX the x-point you want to rotate around
     * @param centerY the y-point you want to rotate around
     */
    public void rotate(double angle, int centerX, int centerY) {
        if (original == null)
            original = new Polygon(shape.xpoints, shape.ypoints, shape.npoints);
        shape = new Polygon();
        AffineTransform transform = AffineTransform.getRotateInstance(angle, centerX, centerY);
        for (int i = 0; i < original.npoints; i++) {
            Point2D p = transform.transform(new Point(original.xpoints[i], original.ypoints[i]), null);
            shape.addPoint((int) p.getX(), (int) p.getY());
        }
        currentRotation = angle;
        this.centerX = centerX;
        this.centerY = centerY;
    }

    /**
     * rotate the hitbox by a given angle
     *
     * @param angle the angle to rotate by in radians
     */
    public void rotate(double angle) {
        centerX = originalX[0];
        centerY = originalY[0];
        rotate(angle, centerX, centerY);
    }


    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("Hitbox{");
        for (int i = 0; i < shape.npoints; i++)
            str.append(String.format("(%s, %s),", shape.xpoints[i], shape.ypoints[i]));
        str.deleteCharAt(str.length() - 1);
        str.append("}");
        return str.toString();
    }

    public Polygon getShape() {
        return shape;
    }
}
